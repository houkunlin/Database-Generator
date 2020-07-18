# 模板升级到 2.0.0 指南

本次发布涉及到目标变量重构，并且不兼容旧版本的模板信息，因此需要针对代码模板进行升级。

本文仅涉及部分内容，详细的新版模板变量用法请查看 [模板变量用法](./template-document.md)



旧版的 `<gen />` 指令，改为直接调用 `${gen.setType("TYPE")}` 方法，详细说明请查看模板变量 `gen` 变量说明。



## 对象名称

|旧的|新的|
|---|---|
|Entity对象名称<br>`<#assign entityClass = "${table.entityName}${settings.entitySuffix}" />`|使用`${entity.name.entity}`|
|Service对象名称<br/>`<#assign serviceClass = "${table.entityName}${settings.serviceSuffix}" />`|使用`${entity.name.service}`|
|`<#assign serviceImplClass = "${table.entityName}${settings.serviceSuffix}Impl" />`|使用`${entity.name.serviceImpl}`|
|`<#assign daoClass = "${table.entityName}${settings.daoSuffix}" />`|使用`${entity.name.dao}`|
|`<#assign controllerClass = "${table.entityName}${settings.controllerSuffix}" />`|使用`${entity.name.controller}`|
|`<#assign serviceVar = "${table.entityVar}${settings.serviceSuffix}" />`|使用`${entity.name.service.firstLower}`|

## 包名

|旧的|新的|
|---|---|
|`${settings.entityPackage}`|`${entity.packages.entity}`|
|`${settings.dao}`|`${entity.packages.dao}`|
|`${settings.servicePackage}`|`${entity.packages.service}`|
|`${settings.servicePackage}.impl`|`${entity.packages.serviceImpl}`|
|`${settings.controllerPackage}`|`${entity.packages.controller}`|
|---|---|
|`import ${settings.entityPackage}.${entityClass}`|`import ${entity.packages.entity.full}`|
|`import ${settings.servicePackage}.${serviceClass}`|`import ${entity.packages.service.full}`|
|`import ${settings.daoPackage}.${daoClass}`|`import ${entity.packages.dao.full}`|
|---|---|
|实体类导入包|`${entity.packages}`|

## 实体类

| 旧的 | 新的 |
| ---- | ---- |
|导入包 `<#list table.getPackages() as package>import ${package};</#list>`| `${entity.packages}`|
|实体类名称`${table.entityName}`| `${entity.name}` 不含后缀 |
|实体类名称`${table.entityName}${settings.entitySuffix}`| `${entity.name.entity}`含后缀 |
|数据库表名`${table.tableName}`|`${table.name}`|
|Entity注释`${table.comment}`|`${entity.comment}`|
|Entity字段`<#list table.columns as col></list>`|`<#list fields as field></list>`|
|获取Entity字段类型`${col.columnType.shortName}`|`${field.typeName}`|
|获取Entity字段名`${col.fieldName}`|`${field.name}`|
|获取Entity字段注释`${col.comment}`|`${field.comment}`|
|Entity字段首字母大写`${col.fieldMethod}`|`${field.name.firstUpper}`|

