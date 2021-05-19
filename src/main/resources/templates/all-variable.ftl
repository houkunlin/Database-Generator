${gen.setFilepath("")}
${gen.setFilename("all-variable.txt")}

settings.projectPath = ${settings.projectPath}
settings.javaPath = ${settings.javaPath}
settings.sourcesPath = ${settings.sourcesPath}
settings.entitySuffix = ${settings.entitySuffix}
settings.daoSuffix = ${settings.daoSuffix}
settings.serviceSuffix = ${settings.serviceSuffix}
settings.controllerSuffix = ${settings.controllerSuffix}
settings.entityPackage = ${settings.entityPackage}
settings.daoPackage = ${settings.daoPackage}
settings.servicePackage = ${settings.servicePackage}
settings.controllerPackage = ${settings.controllerPackage}
settings.xmlPackage = ${settings.xmlPackage}

developer.author = ${developer.author}
developer.email = ${developer.email}

===================================================
gen.setFilename(p1)
gen.setFilepath(p1)
gen.setType(p1): p1 = entity/dao/service/serviceImpl/controller/xml

Not Set gen.setType() , Default Filename: entity.name + ".java"
Not Set gen.setType() , Default Filepath: settings.sourcesPath + "temp"
Not Set gen.setType() , Default Full Path: settings.sourcesPath + "temp" + entity.name + ".java"

# RULE: settings.javaPath + settings.XXXPackage + entity.name.XXX + settings.XXXSuffix + ".java"
gen.setType("entity") = settings.javaPath + settings.entityPackage + entity.name + settings.entitySuffix + ".java"
gen.setType("dao") = settings.javaPath + settings.daoPackage + entity.name + settings.daoSuffix + ".java"
gen.setType("service") = settings.javaPath + settings.servicePackage + entity.name + settings.serviceSuffix + ".java"
gen.setType("serviceImpl") = settings.javaPath + settings.serviceImplPackage + ".impl/" + entity.name + settings.serviceSuffix + "Impl.java"
gen.setType("controller") = settings.javaPath + settings.controllerPackage + entity.name + settings.controllerSuffix + ".java"
gen.setType("xml") = settings.sourcesPath + settings.xmlPackage + entity.name + settings.daoSuffix + ".xml"

gen.setFilepath("src/main/java/com.test") and gen.setFilename("User.java") = src/main/java/com.test/User.java
Only Call gen.setFilepath("src/main/java/com.test") = "src/main/java/com.test/" + entity.name + ".java"
Only Call gen.setFilename("User.java") = settings.sourcesPath + "temp/User.java"

===================================================
date = ${date}
date.toString("yyyy-MM-dd HH:mm:ss") = ${date.toString("yyyy-MM-dd HH:mm:ss")}
date.now().toString("yyyy-MM-dd HH:mm:ss") = ${date.now().toString("yyyy-MM-dd HH:mm:ss")}

===================================================
include "file-name.ext" OR include "/file-name.ext"
the file must at:
1. "{project.dir}/.idea/generator/templates"
2. "{project.dir}/generator/templates"
3. "{idea.config.path}/extensions/com.github.houkunlin.database.generator/generator/templates"

===================================================
entity.packages = "${entity.packages}"
entity.packages.entity = ${entity.packages.entity}
entity.packages.entity.pack = ${entity.packages.entity.pack}
entity.packages.entity.full = ${entity.packages.entity.full}
entity.packages.service = ${entity.packages.service}
entity.packages.service.pack = ${entity.packages.service.pack}
entity.packages.service.full = ${entity.packages.service.full}
entity.packages.serviceImpl = ${entity.packages.serviceImpl}
entity.packages.serviceImpl.pack = ${entity.packages.serviceImpl.pack}
entity.packages.serviceImpl.full = ${entity.packages.serviceImpl.full}
entity.packages.dao = ${entity.packages.dao}
entity.packages.dao.pack = ${entity.packages.dao.pack}
entity.packages.dao.full = ${entity.packages.dao.full}
entity.packages.controller = ${entity.packages.controller}
entity.packages.controller.pack = ${entity.packages.controller.pack}
entity.packages.controller.full = ${entity.packages.controller.full}

entity.name = ${entity.name}
entity.name.firstUpper = ${entity.name.firstUpper}
entity.name.firstLower = ${entity.name.firstLower}
entity.name.entity = ${entity.name.entity}
entity.name.entity.firstUpper = ${entity.name.entity.firstUpper}
entity.name.entity.firstLower = ${entity.name.entity.firstLower}
entity.name.service = ${entity.name.service}
entity.name.service.firstUpper = ${entity.name.service.firstUpper}
entity.name.service.firstLower = ${entity.name.service.firstLower}
entity.name.serviceImpl = ${entity.name.serviceImpl}
entity.name.serviceImpl.firstUpper = ${entity.name.serviceImpl.firstUpper}
entity.name.serviceImpl.firstLower = ${entity.name.serviceImpl.firstLower}
entity.name.dao = ${entity.name.dao}
entity.name.dao.firstUpper = ${entity.name.dao.firstUpper}
entity.name.dao.firstLower = ${entity.name.dao.firstLower}
entity.name.controller = ${entity.name.controller}
entity.name.controller.firstUpper = ${entity.name.controller.firstUpper}
entity.name.controller.firstLower = ${entity.name.controller.firstLower}

entity.comment = ${entity.comment}
entity.uri = ${entity.uri}

table.name = ${table.name}
table.comment = ${table.comment}

primary.field.name = ${primary.field.name}
primary.field.name.firstUpper = ${primary.field.name.firstUpper}
primary.field.name.firstLower = ${primary.field.name.firstLower}
primary.field.comment = ${primary.field.comment}
primary.field.typeName = ${primary.field.typeName}
(primary.field.dataType.length)!'null' = ${(primary.field.dataType.length)!'null'}
(primary.field.dataType.precision)!'null' = ${(primary.field.dataType.precision)!'null'}
(primary.field.dataType.scale)!'null' = ${(primary.field.dataType.scale)!'null'}
(primary.field.dataType.specification)!'null' = ${(primary.field.dataType.specification)!'null'}
primary.field.fullTypeName = ${primary.field.fullTypeName}
primary.field.primaryKey = ${primary.field.primaryKey?string('true', 'false')}
primary.field.selected = ${primary.field.selected?string('true', 'false')}

primary.column.name = ${primary.column.name}
primary.column.comment = ${primary.column.comment}
primary.column.typeName = ${primary.column.typeName}
(primary.column.dataType.length)!'null' = ${(primary.column.dataType.length)!'null'}
(primary.column.dataType.precision)!'null' = ${(primary.column.dataType.precision)!'null'}
(primary.column.dataType.scale)!'null' = ${(primary.column.dataType.scale)!'null'}
(primary.column.dataType.specification)!'null' = ${(primary.column.dataType.specification)!'null'}
primary.column.fullTypeName = ${primary.column.fullTypeName}
primary.column.primaryKey = ${primary.column.primaryKey?string('true', 'false')}
primary.column.selected = ${primary.column.selected?string('true', 'false')}

# primary.fields is Array
# list primary.fields as field

# primary.columns is Array
# list primary.columns as column


=======================================================================================================================
# fields is Array
# list fields as field
<#list fields as field>
field.name = ${field.name}
field.name.firstUpper = ${field.name.firstUpper}
field.name.firstLower = ${field.name.firstLower}
field.comment = ${field.comment}
field.typeName = ${field.typeName}
field.fullTypeName = ${field.fullTypeName}
field.dataType.length = ${field.dataType.length}
field.dataType.precision = ${field.dataType.precision}
field.dataType.scale = ${field.dataType.scale}
field.dataType.specification = ${field.dataType.specification}
field.primaryKey = ${field.primaryKey?string('true', 'false')}
field.selected = ${field.selected?string('true', 'false')}
--------------------------
field.column.name = ${field.column.name}
field.column.comment = ${field.column.comment}
field.column.typeName = ${field.column.typeName}
field.column.fullTypeName = ${field.column.fullTypeName}
field.column.dataType.length = ${field.column.dataType.length}
field.column.dataType.precision = ${field.column.dataType.precision}
field.column.dataType.scale = ${field.column.dataType.scale}
field.column.dataType.specification = ${field.column.dataType.specification}
field.column.primaryKey = ${field.column.primaryKey?string('true', 'false')}
field.column.selected = ${field.column.selected?string('true', 'false')}
---
</#list>


=======================================================================================================================
# columns is Array
# list columns as column
<#list columns as column>
column.name = ${column.name}
column.comment = ${column.comment}
column.typeName = ${column.typeName}
column.fullTypeName = ${column.fullTypeName}
column.dataType.length = ${column.dataType.length}
column.dataType.precision = ${column.dataType.precision}
column.dataType.scale = ${column.dataType.scale}
column.dataType.specification = ${column.dataType.specification}
column.primaryKey = ${column.primaryKey?string('true', 'false')}
column.selected = ${column.selected?string('true', 'false')}
--------------------------
column.field.name = ${column.field.name}
column.field.name.firstUpper = ${column.field.name.firstUpper}
column.field.name.firstLower = ${column.field.name.firstLower}
column.field.comment = ${column.field.comment}
column.field.typeName = ${column.field.typeName}
column.field.fullTypeName = ${column.field.fullTypeName}
column.field.dataType.length = ${column.field.dataType.length}
column.field.dataType.precision = ${column.field.dataType.precision}
column.field.dataType.scale = ${column.field.dataType.scale}
column.field.dataType.specification = ${column.dataType.specification}
column.field.primaryKey = ${column.field.primaryKey?string('true', 'false')}
column.field.selected = ${column.field.selected?string('true', 'false')}
---
</#list>
