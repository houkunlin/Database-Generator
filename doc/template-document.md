[TOC]

# 模板编写文档
模板中会初始化7个变量，其中有3个变量为配置信息变量，有4个变量为数据库相关信息变量。

## 模板内变量说明
### `settings` 基础设置信息变量
Type: `Object` Class: `com.github.houkunlin.config.Settings`

|字段/方法|类型|说明|
| ----|----|----|
|`projectPath`|String|当前项目路径|
|`javaPath`|String|Java代码路径|
|`sourcesPath`|String|资源文件路径|
|`entitySuffix`|String|Entity 后缀|
|`daoSuffix`|String|Dao 后缀|
|`serviceSuffix`|String|Service 后缀|
|`controllerSuffix`|String|Controller 后缀|
|`entityPackage`|String|Entity 包名|
|`daoPackage`|String|Dao 包名|
|`servicePackage`|String|Service 包名|
|`controllerPackage`|String|Controller 包名|
|`xmlPackage`|String|Mapper XML 包名|

### `developer` 开发者信息变量
Type: `Object` Class: `com.github.houkunlin.config.Developer`

|字段/方法|类型|说明|
| ----|----|----|
|`author`|String|开发者姓名|
|`email`|String|开发者电子邮件|

### `gen` 当前文件类型配置对象
Type: `Object` Class: `com.github.houkunlin.vo.Variable`

|字段/方法|类型|说明|
| ----|----|----|
|`setFilename(String filename)`| |设置保存文件名|
|`setFilepath(String filepath)`| |设置保存文件路径|
|`setType(String type)`| |设置保存文件类型|

设置了 `type` 值后，可忽略不设置 `filename` `filepath` 值，但仅限于以下 `type` 可选值范围：

- `entity` Entity 对象
    - 默认文件名: `${entity.name}${settings.entitySuffix}.java`
    - 默认路径: `${settings.javaPath}/${settings.entityPackage}`
- `dao` Dao 对象
    - 默认文件名: `${entity.name}${settings.daoSuffix}.java`
    - 默认路径: `${settings.javaPath}/${settings.daoPackage}`
- `service` Service 接口对象
    - 默认文件名: `${entity.name}${settings.serviceSuffix}.java`
    - 默认路径: `${settings.javaPath}/${settings.servicePackage}`
- `serviceImpl` Service 实现类
    - 默认文件名: `${entity.name}${settings.serviceSuffix}Impl.java`
     - 默认路径: `${settings.javaPath}/${settings.servicePackage}.impl`
- `controller` Controller 对象
    - 默认文件名: `${entity.name}${settings.controllerSuffix}.java`
    - 默认路径: `${settings.javaPath}/${settings.controllerPackage}`
- `xml` MyBatis Mapper 文件。
    - 默认文件名: `${entity.name}${settings.daoSuffix}.xml`
    - 默认路径: `${settings.sourcesPath}/${settings.xmlPackage}`
- 其他未在以上类型列表中的值
    - 默认文件名: `${entity.name}.java`
    - 默认路径: `${settings.sourcesPath}/temp/`

### `table` 数据库表信息变量
Type: `Object` Class: `com.github.houkunlin.vo.impl.TableImpl` 实现了 `com.github.houkunlin.vo.ITable` 接口

|字段/方法|类型|说明|
| ----|----|----|
|`name`|String|数据库表名名称|
|`comment`|String|数据库表注释内容|
|`dbTable`|com.intellij.database.psi.DbTable|开发工具的内部对象，不建议使用|

### `columns` 数据库表字段列表变量
Type: `List` Class: `com.github.houkunlin.vo.impl.TableColumnImpl` 实现了 `com.github.houkunlin.vo.ITableColumn` 接口

|字段/方法|类型|说明|
| ----|----|----|
|`name`|String|列名|
|`comment`|String|列注释|
|`typeName`|String|列类型信息（短）|
|`fullTypeName`|String|列类型信息（完整）|
|`primaryKey`|boolean|是否是主键|
|`selected`|boolean|是否选中（通过UI勾选）|
|`dbColumn`|com.intellij.database.model.DasColumn|开发工具的内部对象，不建议使用|

### `entity` 实体类信息变量
Type: `Object` Class: `com.github.houkunlin.vo.impl.EntityImpl` 实现了 `com.github.houkunlin.vo.IEntity` 接口

|字段/方法|类型|说明|
| ----|----|----|
|`name`|EntityName|实体名称对象|
|`comment`|String|实体注释对象（可通过UI修改）|
|`packages`|EntityPackage|实体字段中需要引入的包信息|

### `fields` 实体对象字段列表信息变量
Type: `List` Class: `com.github.houkunlin.vo.impl.EntityFieldImpl` 实现了 `com.github.houkunlin.vo.IEntityField` 接口

|字段/方法|类型|说明|
| ----|----|----|
|`name`|IName|字段/方法对象。直接使用该对象将调用 toString() 方法返回驼峰格式、首字母小写的字段/方法字符串信息|
|`comment`|String|字段注释（可通过UI修改）|
|`typeName`|String|字段类型（短）|
|`fullTypeName`|String|字段类型（完整）|
|`primaryKey`|boolean|是否是主键|
|`selected`|boolean|是否选中（通过UI勾选）|

## 模板其他相关对象说明
### IName 名称接口
Class: `com.github.houkunlin.vo.IName`

|字段/方法|类型|说明|
| ----|----|----|
|`toString()`|String|直接使用该对象将调用 toString() 方法，该方法根据不同使用场景将返回不同的值，主要区别在首字母是否大小写问题|
|`firstLower()`|String|名称首字母小写|
|`firstUpper()`|String|名称首字母大写|
|`var()`|String|名称首字母小写，同 `firstLower()`，用意：返回变量名称|


### `EntityName` 实体类名称对象
Class: `com.github.houkunlin.vo.impl.EntityName` 实现了 `com.github.houkunlin.vo.IName` 接口

|字段/方法|类型|说明|
| ----|----|----|
|`value`|String|不含Entity后缀的实体类名称，也就是UI中输入框的Entity名称|
|`toString()`|String|返回 `value` 字段|
|`entity`|IName|Entity 对象名称，`toString()` 返回包含Entity后缀的名称（驼峰格式、首字母大写）|
|`service`|IName|Service 对象名称，`toString()` 返回包含Service后缀的名称（驼峰格式、首字母大写）|
|`serviceImpl`|IName|ServiceImpl 对象名称，`toString()` 返回包含ServiceImpl后缀的名称（驼峰格式、首字母大写）|
|`dao`|IName|Dao 对象名称，`toString()` 返回包含Dao后缀的名称（驼峰格式、首字母大写）|
|`controller`|IName|Controller 对象名称，`toString()` 返回包含Controller后缀的名称（驼峰格式、首字母大写）|

### `EntityPackage` 实体类包对象
Class: `com.github.houkunlin.vo.impl.EntityPackage`

|字段/方法|类型|说明|
| ----|----|----|
|`list`|HashSet<String>|Entity 字段需要导入的包列表，不含`import`字符串|
|`toString()`|String|直接使用该对象将会调用 toString() 方法返回Java语言的导入包代码（导入list中的所有包，含`import`字符串）|
|`entity`|EntityPackageInfo|Entity 包信息|
|`service`|EntityPackageInfo|Service 包信息|
|`serviceImpl`|EntityPackageInfo|ServiceImpl 包信息|
|`dao`|EntityPackageInfo|Dao 包信息|
|`controller`|EntityPackageInfo|Controller 包信息|

### `EntityPackageInfo` 实体类包信息对象
Class: `com.github.houkunlin.vo.impl.EntityPackageInfo`

|字段/方法|类型|说明|
| ----|----|----|
|`pack`|String|Entity/Service/Controller/Dao 对象所在包的包名称|
|`toString()`|String|返回 `pack` 字段|
|`full`|String|对象完整的包路径，即：包名称+对象名称|

## 示例代码
数据库表名：`user_info`

|代码|返回示例|
|---|---|
|`${table.name}`|user_info|
|`${entity.name}`|UserInfo|
|`${entity.name.var()}`|userInfo|
|`${entity.name.entity}`|UserInfoEntity|
|`${entity.name.entity.var()}`|userInfoEntity|
|`${entity.name.service}`|UserInfoService|
|`${entity.name.service.var()}`|userInfoService|
|`${entity.name.serviceImpl}`|UserInfoServiceImpl|
|`${entity.name.serviceImpl.var()}`|userInfoServiceImpl|
|`${entity.packages}`|`import java.math.BigDecimal;import java.util.Date;`|
|`${entity.packages.list}`|\["java.math.BigDecimal","java.util.Date"\]|
|`${entity.packages.entity}`|com.example.entity|
|`${entity.packages.entity.pack}`|com.example.entity|
|`${entity.packages.entity.full}`|com.example.entity.UserInfoEntity|
|`${entity.packages.service}`|com.example.service|
|`${entity.packages.service.pack}`|com.example.service|
|`${entity.packages.service.full}`|com.example.service.UserInfoService|
|`${entity.packages.serviceImpl}`|com.example.service.impl|
|`${entity.packages.serviceImpl.pack}`|com.example.service.impl|
|`${entity.packages.serviceImpl.full}`|com.example.service.impl.UserInfoServiceImpl|

字段名称：`user_address` 类型：`varchar(255)`

|代码|返回示例|
|---|---|
|`${field.name}`|userAddress|
|`${field.name.var()}`|userAddress|
|`${field.name.firstUpper()}`|UserAddress|
|`${field.typeName}`|String，该值将根据 `types.json` 配置决定|
|`${field.fullTypeName}`|java.lang.String，该值将根据 `types.json` 配置决定|
|`${column.name}`|user_address|
|`${column.typeName}`|varchar|
|`${column.fullTypeName}`|varchar(255)|