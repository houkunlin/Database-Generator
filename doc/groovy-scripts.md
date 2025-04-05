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

boolean hasAllSuperFields(List<String> fields, List<String> superFields) {
    return superFields.every { fields.contains(it) }
}
```
需要注意的是，脚本中不能引入外部依赖，即不能使用`import`导入类，但可以通过简单的接口定义，来实现类型约束。

## 实现
通过扫描插件目录下的`scripts`文件夹，读取其中的脚本文件，将所有方法反射后通过统一的`AnyCallback`函数式接口包装，之后注册到Map中，对外提供`get`方法通过方法名获取`AnyCallback`执行函数。

## velocity

因为`velocity`在Idea中提供很好的语法高亮支持，所以其是开发者常用的模板，此处也是引入脚本的最初模板。

因此，后续的脚本方法调用，会以`velocity`中使用为基准。

```vm
${scripts.get("hello").call()}
## get 方法可以简化使用
${scripts.hello.call()}
${scripts.say.call("hello")}
${scripts.xxx.call(arg1, arg2, arg3)}
```

## beetl

`beetl`在3.17以后，引入安全策略，默认不允许直接调用Java方法和属性，虽然通过调整安全策略放开限制，但是对于多级调用并不适用。

在测试过程中，仅能对上下文中的直接传入的变量可以自由访问属性和方法，对于变量的返回值，就无法再访问其方法。

好在其支持注册自定义函数，通过实现其内置的`Function`接口，并指定命名空间，即可模拟出原生调用的形式，看起来比`velocity`更符合更简洁。
```beetl
${scripts.hello()}
${scripts.say("hello")}
${scripts.xxx(arg1, arg2, arg3)}
```
## freemarker（不推荐）
`freemarker`对于java方法的支持就有些不如人意了，虽然最终实现了类似`velocity`的效果，但实际上完全不能等同。
```ftl
${scripts.get("hello").call()}
## get 方法可以简化使用
${scripts.hello.call()}
${scripts.say.call("hello")}
${scripts.xxx.call(arg1, arg2, arg3)}
```
其所有变量在传入时会被自动包装成`TemplateModel`，然后在运行时会有一个自动解包的过程。

比如说，对于`int`类型就不能被识别，因为其内部仅存在`SimpleNumber`类型，对应Java中的`BigDecimal`。

所以，在使用脚本时需要格外注意参数类型，而且无法保证会不会有其他类型映射的问题，因此不推荐在`freemarker`中使用脚本。
