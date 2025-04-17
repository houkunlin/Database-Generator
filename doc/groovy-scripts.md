# Groovy 脚本

为插件提供一种用户自定义的拓展功能，避免在模板中编写大量的逻辑，引入了 Groovy 脚本。
需要注意的是，对于`beetl`、`freemarker`以及`velocity`来说，在调用脚本时，会有一些差异，这是模板差异导致的。

## 使用场景

对于业务表中的公共字段通常可以使用父类或接口约束，如：`BaseEntity`，对于这些表的实体类实现，需要动态的通过表字段是否完全包含公共字段时，进行相应的处理。

这在模板中定义指令或者宏过于复杂，也不应该在模板中编写逻辑。而使用`groovy`则可以轻松实现。

```groovy
interface IEntityField {
    String name
}

List<String> getAllFieldNames(List<IEntityField> fields) {
    return fields.collect { it.name }
}
```
需要注意的是，脚本中不能引入外部依赖，即不能使用`import`导入类，但可以通过简单的接口定义，来实现类型约束。

## 实现
通过扫描插件目录下的`scripts`文件夹，读取其中的脚本文件， 通过`GroovyScriptEngine`可将单个脚本解析为`Class`，通过反射创建一个脚本实例，并将其缓存到`Map`中。
(~~将所有方法反射后通过统一的`AnyCallback`函数式接口包装，之后注册到Map中，对外提供`get`方法通过方法名获取`AnyCallback`执行函数。~~ )

在当前版本中，脚本文件将被应用为命名空间，如,`scripts.foo.bar()`对应脚本结构：
```text
scripts
|-- foo.groovy
|---- bar()
```
## velocity

因为`velocity`在Idea中提供很好的语法高亮支持，所以其是开发者常用的模板，此处也是引入脚本的最初模板。

因此，后续的脚本方法调用，会以`velocity`中使用为基准。

通过`GroovyScriptEngine`创建的脚本实例，以及`velocity`与`Java`的互操作性，现已废弃语法: ~~`${scripts.foo.bar.call(args...)}`~~ 。

```vm
// 通过get方法调用具体的脚本
${scripts.get("foo")} // foo.groovy
## get 方法可以简化使用
${scripts.foo.bar()} // def bar()
${scripts.xxx.abc(args...)}
```

## beetl

`beetl`在3.17以后，引入安全策略，默认不允许直接调用Java方法和属性，虽然通过调整安全策略放开限制，但是对于多级调用并不适用。

好在其支持注册自定义函数，通过调用`registerFunctionPackage`可以直接注册变量的所有`public`方法到上下文，（~~其内置的`Function`接口，并指定命名空间~~），即可模拟出原生调用的形式。

**注意：** 
1. 始终需要注意脚本中属性的可见性，将需要访问的属性标记为`public`，以避免`AttributeAccess`异常。
2. 直接调用脚本方法

```beetl
${scripts.foo} // foo.groovy
${scripts.foo.bar()} // def bar()
${scripts.xxx.abc(args...)}
// 若返回一个对象
<% 
var target = scripts.foo.get()
%>
// 需要使用 @ 标记访问一个对象的属性或方法，并且其可见性应为`public`
${@target.foo}
```

## freemarker（不推荐）

通过创建脚本实例，也可以直接在`freemarker`中通过上下文共享变量。

```ftl
${scripts.get("foo")} // foo.groovy
## get 方法可以简化使用
${scripts.foo.bar()} // def bar()
${scripts.xxx.abc(args...)}
```

**注意：** 
1. 对于脚本运算后的对象属性的调用，必须包含`Getter`方法，否则无法访问。
2. 虽然也可以调用`Java`方法，但是变量在传入时会被自动包装成`TemplateModel`，然后在运行时会有一个自动解包的过程。
比如说，对于`int`类型就不能被识别，因为其内部仅存在`SimpleNumber`类型，对应Java中的`BigDecimal`。

所以，在使用脚本时需要格外注意参数类型，而且无法保证会不会有其他类型映射的问题，因此不推荐在`freemarker`中使用脚本。

## 脚本实践
当需要根据表的公共字段动态判断父类信息时，实体类定义如下：
```vtl
## Entity.java.vm
${gen.setType("entity")} ##
#set($parent = ${scripts.Parents.getSpec($fields)})##
// parent is $parent ## only for test
// parent is ${parent.name} ## only for test
package ${entity.packages.entity};

${entity.packages}

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
${scripts.Utils.importAll($parent.imports)}

/**
* 实体类：${entity.comment}#if($table.comment.trim.length gt 0 && $entity.comment != $table.comment) (${table.comment})#end
 *
 * @author ${developer.author}
 */
@Data
    ${scripts.Utils.annotationAll($parent.annotations)}##
@NoArgsConstructor
@AllArgsConstructor
public class ${entity.name.entity} ${scripts.Utils.extendsOne($parent.name)}  implements Serializable {
    #foreach($field in $fields)
        #if($field.selected && ($parent.nonSuperField(${field.name})))

            /**
             * ${field.comment}
                #if($field.column.comment.trim.length gt 0 && $field.comment != $field.column.comment) * <p>数据库字段说明：${field.column.comment}</p>#end
             */
            private ${field.typeName} ${field.name};
        #end
    #end
}
```
定义一个脚本，用于获取父类规格信息，并返回一个`ParentSpec`对象，其中包含了父类的信息，如名称，字段，导入，注解等。

其中，对于业务字段始终包含逻辑删除以及拓展字段，而基本字段则包含创建信息和更新信息。

```groovy
// Parents.groovy
interface IEntityField {
    String name
}

private def getAllFieldNames(List<IEntityField> fields) {
    return fields.collect { it.name.toString() }
}

def getSpec(List<IEntityField> fields) {
    def fieldNames = getAllFieldNames(fields)
    if (fieldNames.containsAll(ParentSpec.biz.fields)) {
        return ParentSpec.biz
    }
    if (fieldNames.containsAll(ParentSpec.base.fields)) {
        return ParentSpec.base
    }
    return ParentSpec.non
}

class ParentSpec {
    String name
    List<String> fields
    List<String> imports
    List<String> annotations

    def nonSuperField(String field) {
        return !fields.contains(field)
    }

    private static superFields = ["id", "createdBy", "createdAt", "updatedBy", "updatedAt"]
    private static superImports = ["lombok.experimental.SuperBuilder", "lombok.EqualsAndHashCode"]
    private static superAnnotations = ["SuperBuilder", "EqualsAndHashCode(callSuper = true)"]

    static biz = new ParentSpec().tap {
        name = "BizEntity"
        fields = ["deletedBy", "deletedAt", "deleted", "remark", "ext1", "ext2", "ext3", "ext4"] + superFields
        imports = ["com.xxx.entity.BizEntity"] + superImports
        annotations = superAnnotations
    }

    static base = new ParentSpec().tap {
        name = "BaseEntity"
        fields = superFields
        imports = ["com.xxx.entity.BaseEntity"] + superImports
        annotations = superAnnotations
    }

    static non = new ParentSpec().tap {
        name: ""
        fields: []
        imports: ["lombok.Builder"]
        annotations: ["Builder"]
    }
}
```
定义辅助类，用于简化模板的编写。
```groovy
// Utils.groovy
def importAll(List<String> list) {
    return writeln(list) { "import " + it + ";" }
}

def annotationAll(List<String> list) {
    return writeln(list, {
        "@" + it
    })
}

def extendsOne(String name) {
    if (name) {
        return " extends $name "
    }
    return ""
}

def writeln(List<String> list, Closure<String> closure) {
    if (list == null || list.isEmpty()) {
        return ""
    }
    return list.collect(closure).join("\n")
}

```
