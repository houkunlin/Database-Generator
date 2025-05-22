${gen.setFilepath("")}
${gen.setFilename("all-variable.md")}

# 模板变量及渲染结果示例

|变量项|变量值|
|----|----|
|`${r'$'}{settings.projectPath}`|${settings.projectPath}|
|`${r'$'}{settings.javaPath}`|${settings.javaPath}|
|`${r'$'}{settings.resourcesPath}`|${settings.resourcesPath}|
|`${r'$'}{developer.author}`|${developer.author}|
|`${r'$'}{developer.email}`|${developer.email}|

```
gen.setFilename(p1)
gen.setFilepath(p1)
gen.setType(p1): p1 = entity/dao/service/serviceImpl/controller/xml   or   custom type

Not Set gen.setType() , Default Filename: entity.name + ".java"
Not Set gen.setType() , Default Filepath: settings.resourcesPath + "temp"
Not Set gen.setType() , Default Full Path: settings.resourcesPath + "temp" + entity.name + ".java"

# RULE: FileType.path + FileType.packageName + entity.name.XXX + FileType.suffix + ".java"
gen.setType("entity") = FileType.path + FileType.packageName + entity.name + FileType.suffix + ".java"
gen.setType("dao") = FileType.path + FileType.packageName + entity.name + FileType.suffix + ".java"
gen.setType("service") = FileType.path + FileType.packageName + entity.name + FileType.suffix + ".java"
gen.setType("serviceImpl") = FileType.path + FileType.packageName + ".impl/" + entity.name + FileType.suffix + ".java"
gen.setType("controller") = FileType.path + FileType.packageName + entity.name + FileType.suffix + ".java"
gen.setType("xml") = FileType.path + FileType.packageName + entity.name + FileType.suffix + ".xml"

gen.setFilepath("src/main/java/com.test") and gen.setFilename("User.java") = src/main/java/com.test/User.java
Only Call gen.setFilepath("src/main/java/com.test") = "src/main/java/com.test/" + entity.name + ".java"
Only Call gen.setFilename("User.java") = settings.resourcesPath + "temp/User.java"
```

---
include "file-name.ext" OR include "/file-name.ext"

the file must at:

1. "{project.dir}/.idea/generator/templates"
2. "{project.dir}/generator/templates"
3. "{idea.config.path}/extensions/com.github.houkunlin.database.generator/generator/templates"

---

## date

|变量项|变量值|
|----|----|
|`${r'$'}{date}`|${date}|
|`${r'$'}{date.toString("yyyy-MM-dd HH:mm:ss")}`|${date.toString("yyyy-MM-dd HH:mm:ss")}|
|`${r'$'}{date.now().toString("yyyy-MM-dd HH:mm:ss")}`|${date.now().toString("yyyy-MM-dd HH:mm:ss")}|

## entity

|变量项|变量值|
|----|----|
|`${r'$'}{entity.comment}`|${entity.comment}|
|`${r'$'}{entity.uri}`|${entity.uri}|

### entity.name

|变量项|变量值|
|----|----|
|`${r'$'}{entity.name}`|${entity.name}|
|`${r'$'}{entity.name.firstUpper}`|${entity.name.firstUpper}|
|`${r'$'}{entity.name.firstLower}`|${entity.name.firstLower}|
|`${r'$'}{entity.name.entity}`|${entity.name.entity}|
|`${r'$'}{entity.name.service}`|${entity.name.service}|
|`${r'$'}{entity.name.serviceImpl}`|${entity.name.serviceImpl}|
|`${r'$'}{entity.name.dao}`|${entity.name.dao}|
|`${r'$'}{entity.name.controller}`|${entity.name.controller}|

#### entity.name.entity

|变量项|变量值|
|----|----|
|`${r'$'}{entity.name.entity}`|${entity.name.entity}|
|`${r'$'}{entity.name.entity.firstUpper}`|${entity.name.entity.firstUpper}|
|`${r'$'}{entity.name.entity.firstLower}`|${entity.name.entity.firstLower}|

#### entity.name.service

|变量项|变量值|
|----|----|
|`${r'$'}{entity.name.service}`|${entity.name.service}|
|`${r'$'}{entity.name.service.firstUpper}`|${entity.name.service.firstUpper}|
|`${r'$'}{entity.name.service.firstLower}`|${entity.name.service.firstLower}|

#### entity.name.serviceImpl

|变量项|变量值|
|----|----|
|`${r'$'}{entity.name.serviceImpl}`|${entity.name.serviceImpl}|
|`${r'$'}{entity.name.serviceImpl.firstUpper}`|${entity.name.serviceImpl.firstUpper}|
|`${r'$'}{entity.name.serviceImpl.firstLower}`|${entity.name.serviceImpl.firstLower}|

#### entity.name.dao

|变量项|变量值|
|----|----|
|`${r'$'}{entity.name.dao}`|${entity.name.dao}|
|`${r'$'}{entity.name.dao.firstUpper}`|${entity.name.dao.firstUpper}|
|`${r'$'}{entity.name.dao.firstLower}`|${entity.name.dao.firstLower}|

#### entity.name.controller

|变量项|变量值|
|----|----|
|`${r'$'}{entity.name.controller}`|${entity.name.controller}|
|`${r'$'}{entity.name.controller.firstUpper}`|${entity.name.controller.firstUpper}|
|`${r'$'}{entity.name.controller.firstLower}`|${entity.name.controller.firstLower}|

### entity.packages

```
// ${r'$'}{entity.packages} =

${entity.packages}
```

|变量项|变量值|
|----|----|
|`${r'$'}{entity.packages.entity}`|${entity.packages.entity}|
|`${r'$'}{entity.packages.service}`|${entity.packages.service}|
|`${r'$'}{entity.packages.serviceImpl}`|${entity.packages.serviceImpl}|
|`${r'$'}{entity.packages.dao}`|${entity.packages.dao}|
|`${r'$'}{entity.packages.controller}`|${entity.packages.controller}|

#### entity.packages.entity

|变量项|变量值|
|----|----|
|`${r'$'}{entity.packages.entity}`|${entity.packages.entity}|
|`${r'$'}{entity.packages.entity.pack}`|${entity.packages.entity.pack}|
|`${r'$'}{entity.packages.entity.full}`|${entity.packages.entity.full}|

#### entity.packages.service

|变量项|变量值|
|----|----|
|`${r'$'}{entity.packages.service}`|${entity.packages.service}|
|`${r'$'}{entity.packages.service.pack}`|${entity.packages.service.pack}|
|`${r'$'}{entity.packages.service.full}`|${entity.packages.service.full}|

#### entity.packages.serviceImpl

|变量项|变量值|
|----|----|
|`${r'$'}{entity.packages.serviceImpl}`|${entity.packages.serviceImpl}|
|`${r'$'}{entity.packages.serviceImpl.pack}`|${entity.packages.serviceImpl.pack}|
|`${r'$'}{entity.packages.serviceImpl.full}`|${entity.packages.serviceImpl.full}|

#### entity.packages.dao

|变量项|变量值|
|----|----|
|`${r'$'}{entity.packages.dao}`|${entity.packages.dao}|
|`${r'$'}{entity.packages.dao.pack}`|${entity.packages.dao.pack}|
|`${r'$'}{entity.packages.dao.full}`|${entity.packages.dao.full}|

#### entity.packages.controller

|变量项|变量值|
|----|----|
|`${r'$'}{entity.packages.controller}`|${entity.packages.controller}|
|`${r'$'}{entity.packages.controller.pack}`|${entity.packages.controller.pack}|
|`${r'$'}{entity.packages.controller.full}`|${entity.packages.controller.full}|

## table

|变量项|变量值|
|----|----|
|`${r'$'}{table.name}`|${table.name}|
|`${r'$'}{table.comment}`|${table.comment}|

## primary.field

|变量项|变量值|
|----|----|
|`${r'$'}{primary.field.name}`|${primary.field.name}|
|`${r'$'}{primary.field.name.firstUpper}`|${primary.field.name.firstUpper}|
|`${r'$'}{primary.field.name.firstLower}`|${primary.field.name.firstLower}|
|`${r'$'}{primary.field.comment}`|${primary.field.comment}|
|`${r'$'}{primary.field.typeName}`|${primary.field.typeName}|
|`${r'$'}{(primary.field.dataType.length)!'null'}`|${(primary.field.dataType.length)!'null'}|
|`${r'$'}{(primary.field.dataType.precision)!'null'}`|${(primary.field.dataType.precision)!'null'}|
|`${r'$'}{(primary.field.dataType.scale)!'null'}`|${(primary.field.dataType.scale)!'null'}|
|`${r'$'}{(primary.field.dataType.specification)!'null'}`|${(primary.field.dataType.specification)!'null'}|
|`${r'$'}{primary.field.fullTypeName}`|${primary.field.fullTypeName}|
|`${r'$'}{primary.field.primaryKey}`|${primary.field.primaryKey?string('true', 'false')}|
|`${r'$'}{primary.field.selected}`|${primary.field.selected?string('true', 'false')}|

## primary.column

|变量项|变量值|
|----|----|
|`${r'$'}{primary.column.name}`|${primary.column.name}|
|`${r'$'}{primary.column.comment}`|${primary.column.comment}|
|`${r'$'}{primary.column.typeName}`|${primary.column.typeName}|
|`${r'$'}{(primary.column.dataType.length)!'null'}`|${(primary.column.dataType.length)!'null'}|
|`${r'$'}{(primary.column.dataType.precision)!'null'}`|${(primary.column.dataType.precision)!'null'}|
|`${r'$'}{(primary.column.dataType.scale)!'null'}`|${(primary.column.dataType.scale)!'null'}|
|`${r'$'}{(primary.column.dataType.specification)!'null'}`|${(primary.column.dataType.specification)!'null'}|
|`${r'$'}{primary.column.fullTypeName}`|${primary.column.fullTypeName}|
|`${r'$'}{primary.column.primaryKey}`|${primary.column.primaryKey?string('true', 'false')}|
|`${r'$'}{primary.column.selected}`|${primary.column.selected?string('true', 'false')}|

## ${r'<#'}list primary.fields as field>

```
${r'<#'}list primary.fields as field>
field.name = ${r'$'}{field.name}
field.name.firstUpper = ${r'$'}{field.name.firstUpper}
field.name.firstLower = ${r'$'}{field.name.firstLower}
field.comment = ${r'$'}{field.comment}
field.typeName = ${r'$'}{field.typeName}
field.fullTypeName = ${r'$'}{field.fullTypeName}
field.dataType.length = ${r'$'}{field.dataType.length}
field.dataType.precision = ${r'$'}{field.dataType.precision}
field.dataType.scale = ${r'$'}{field.dataType.scale}
field.dataType.specification = ${r'$'}{field.dataType.specification}
field.primaryKey = ${r'$'}{field.primaryKey?string('true', 'false')}
field.selected = ${r'$'}{field.selected?string('true', 'false')}
${r'</#'}list>
```

| 序号 |`${r'$'}{field.name}`|`${r'$'}{field.name.firstUpper}`|`${r'$'}{field.name.firstLower}`|`${r'$'}{field.comment}`|`${r'$'}{field.typeName}`|`${r'$'}{field.fullTypeName}`|`${r'$'}{field.dataType.length}`|`${r'$'}{field.dataType.precision}`|`${r'$'}{field.dataType.scale}`|`${r'$'}{field.dataType.specification}`|`${r'$'}{field.primaryKey}`|`${r'$'}{field.selected}`|
|----|----|----|----|----|----|----|----|----|----|----|----|----|
<#list primary.fields as field>
|${field?index + 1}|${field.name}|${field.name.firstUpper}|${field.name.firstLower}|${field.comment}|${field.typeName}|${field.fullTypeName}|${field.dataType.length}|${field.dataType.precision}|${field.dataType.scale}|${field.dataType.specification}|${field.primaryKey?string('true', 'false')}|${field.selected?string('true', 'false')}|
</#list>

## ${r'<#'}list primary.columns as column>

```
${r'<#'}list primary.columns as column>
column.name = ${r'$'}{column.name}
column.comment = ${r'$'}{column.comment}
column.typeName = ${r'$'}{column.typeName}
column.fullTypeName = ${r'$'}{column.fullTypeName}
column.dataType.length = ${r'$'}{column.dataType.length}
column.dataType.precision = ${r'$'}{column.dataType.precision}
column.dataType.scale = ${r'$'}{column.dataType.scale}
column.dataType.specification = ${r'$'}{column.dataType.specification}
column.primaryKey = ${r'$'}{column.primaryKey?string('true', 'false')}
column.selected = ${r'$'}{column.selected?string('true', 'false')}
${r'</#'}list>
```

| 序号 | `${r'$'}{column.name}` | `${r'$'}{column.comment}` |  `${r'$'}{column.typeName}` |  `${r'$'}{column.fullTypeName}` |  `${r'$'}{column.dataType.length}` |  `${r'$'}{column.dataType.precision}` |  `${r'$'}{column.dataType.scale}` |  `${r'$'}{column.dataType.specification}` |  `${r'$'}{column.primaryKey}` |  `${r'$'}{column.selected}` |
|----|----|----|----|----|----|----|----|----|----|----|
<#list primary.columns as column>
|${column?index + 1}|${column.name}|${column.comment}|${column.typeName}|${column.fullTypeName}|${column.dataType.length}|${column.dataType.precision}|${column.dataType.scale}|${column.dataType.specification}|${column.primaryKey?string('true', 'false')}|${column.selected?string('true', 'false')}|
</#list>

## ${r'<#'}list fields as field>

### field 用法

```
${r'<#'}list fields as field>
field.name = ${r'$'}{field.name}
field.name.firstUpper = ${r'$'}{field.name.firstUpper}
field.name.firstLower = ${r'$'}{field.name.firstLower}
field.comment = ${r'$'}{field.comment}
field.typeName = ${r'$'}{field.typeName}
field.fullTypeName = ${r'$'}{field.fullTypeName}
field.dataType.length = ${r'$'}{field.dataType.length}
field.dataType.precision = ${r'$'}{field.dataType.precision}
field.dataType.scale = ${r'$'}{field.dataType.scale}
field.dataType.specification = ${r'$'}{field.dataType.specification}
field.primaryKey = ${r'$'}{field.primaryKey?string('true', 'false')}
field.selected = ${r'$'}{field.selected?string('true', 'false')}
${r'</#'}list>
```

| 序号 |`${r'$'}{field.name}`|`${r'$'}{field.name.firstUpper}`|`${r'$'}{field.name.firstLower}`|`${r'$'}{field.comment}`|`${r'$'}{field.typeName}`|`${r'$'}{field.fullTypeName}`|`${r'$'}{field.dataType.length}`|`${r'$'}{field.dataType.precision}`|`${r'$'}{field.dataType.scale}`|`${r'$'}{field.dataType.specification}`|`${r'$'}{field.primaryKey}`|`${r'$'}{field.selected}`|
|----|----|----|----|----|----|----|----|----|----|----|----|----|
<#list fields as field>
|${field?index + 1}|${field.name}|${field.name.firstUpper}|${field.name.firstLower}|${field.comment}|${field.typeName}|${field.fullTypeName}|${field.dataType.length}|${field.dataType.precision}|${field.dataType.scale}|${field.dataType.specification}|${field.primaryKey?string('true', 'false')}|${field.selected?string('true', 'false')}|
</#list>

### field.column 用法

```
${r'<#'}list fields as field>
field.column.name = ${r'$'}{field.column.name}
field.column.comment = ${r'$'}{field.column.comment}
field.column.typeName = ${r'$'}{field.column.typeName}
field.column.fullTypeName = ${r'$'}{field.column.fullTypeName}
field.column.dataType.length = ${r'$'}{field.column.dataType.length}
field.column.dataType.precision = ${r'$'}{field.column.dataType.precision}
field.column.dataType.scale = ${r'$'}{field.column.dataType.scale}
field.column.dataType.specification = ${r'$'}{field.column.dataType.specification}
field.column.primaryKey = ${r'$'}{field.column.primaryKey?string('true', 'false')}
field.column.selected = ${r'$'}{field.column.selected?string('true', 'false')}
${r'</#'}list>
```

| 序号 | `${r'$'}{field.column.name}` | `${r'$'}{field.column.comment}` |  `${r'$'}{field.column.typeName}` |  `${r'$'}{field.column.fullTypeName}` |  `${r'$'}{field.column.dataType.length}` |  `${r'$'}{field.column.dataType.precision}` |  `${r'$'}{field.column.dataType.scale}` |  `${r'$'}{field.column.dataType.specification}` |  `${r'$'}{field.column.primaryKey}` |  `${r'$'}{field.column.selected}` |
|----|----|----|----|----|----|----|----|----|----|----|
<#list fields as field>
|${field?index + 1}|${field.column.name}|${field.column.comment}|${field.column.typeName}|${field.column.fullTypeName}|${field.column.dataType.length}|${field.column.dataType.precision}|${field.column.dataType.scale}|${field.column.dataType.specification}|${field.column.primaryKey?string('true', 'false')}|${field.column.selected?string('true', 'false')}|
</#list>

## ${r'<#'}list columns as column>

### column 用法

```
${r'<#'}list columns as column>
column.name = ${r'$'}{column.name}
column.comment = ${r'$'}{column.comment}
column.typeName = ${r'$'}{column.typeName}
column.fullTypeName = ${r'$'}{column.fullTypeName}
column.dataType.length = ${r'$'}{column.dataType.length}
column.dataType.precision = ${r'$'}{column.dataType.precision}
column.dataType.scale = ${r'$'}{column.dataType.scale}
column.dataType.specification = ${r'$'}{column.dataType.specification}
column.primaryKey = ${r'$'}{column.primaryKey?string('true', 'false')}
column.selected = ${r'$'}{column.selected?string('true', 'false')}
${r'</#'}list>
```

| 序号 | `${r'$'}{column.name}` | `${r'$'}{column.comment}` |  `${r'$'}{column.typeName}` |  `${r'$'}{column.fullTypeName}` |  `${r'$'}{column.dataType.length}` |  `${r'$'}{column.dataType.precision}` |  `${r'$'}{column.dataType.scale}` |  `${r'$'}{column.dataType.specification}` |  `${r'$'}{column.primaryKey}` |  `${r'$'}{column.selected}` |
|----|----|----|----|----|----|----|----|----|----|----|
<#list columns as column>
|${column?index + 1}|${column.name}|${column.comment}|${column.typeName}|${column.fullTypeName}|${column.dataType.length}|${column.dataType.precision}|${column.dataType.scale}|${column.dataType.specification}|${column.primaryKey?string('true', 'false')}|${column.selected?string('true', 'false')}|
</#list>

### column.field 用法

```
${r'<#'}list columns as column>
column.field.name = ${r'$'}{column.field.name}
column.field.name.firstUpper = ${r'$'}{column.field.name.firstUpper}
column.field.name.firstLower = ${r'$'}{column.field.name.firstLower}
column.field.comment = ${r'$'}{column.field.comment}
column.field.typeName = ${r'$'}{column.field.typeName}
column.field.fullTypeName = ${r'$'}{column.field.fullTypeName}
column.field.dataType.length = ${r'$'}{column.field.dataType.length}
column.field.dataType.precision = ${r'$'}{column.field.dataType.precision}
column.field.dataType.scale = ${r'$'}{column.field.dataType.scale}
column.field.dataType.specification = ${r'$'}{column.dataType.specification}
column.field.primaryKey = ${r'$'}{column.field.primaryKey?string('true', 'false')}
column.field.selected = ${r'$'}{column.field.selected?string('true', 'false')}
${r'</#'}list>
```

| 序号 |`${r'$'}{column.field.name}`|`${r'$'}{column.field.name.firstUpper}`|`${r'$'}{column.field.name.firstLower}`|`${r'$'}{column.field.comment}`|`${r'$'}{column.field.typeName}`|`${r'$'}{column.field.fullTypeName}`|`${r'$'}{column.field.dataType.length}`|`${r'$'}{column.field.dataType.precision}`|`${r'$'}{column.field.dataType.scale}`|`${r'$'}{column.dataType.specification}`|`${r'$'}{column.field.primaryKey}`|`${r'$'}{column.field.selected}`|
|----|----|----|----|----|----|----|----|----|----|----|----|----|
<#list columns as column>
|${column?index + 1}|${column.field.name}|${column.field.name.firstUpper}|${column.field.name.firstLower}|${column.field.comment}|${column.field.typeName}|${column.field.fullTypeName}|${column.field.dataType.length}|${column.field.dataType.precision}|${column.field.dataType.scale}|${column.dataType.specification}|${column.field.primaryKey?string('true', 'false')}|${column.field.selected?string('true', 'false')}|
</#list>
