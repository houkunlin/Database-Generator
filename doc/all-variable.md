# 模板变量及渲染结果示例

```sql
-- 以下是本示例对应的 SQL 表结构
CREATE TABLE "public"."test-table" (
  "id" int2 NOT NULL,
  "int2" int2,
  "int4" int4,
  "int8" int8,
  "bigserial" bigserial,
  "bit" bit(1),
  "bool" bool,
  "box" box,
  "bytea" bytea,
  "char" char(1) COLLATE "pg_catalog"."default",
  "cidr" cidr,
  "circle" circle,
  "date" date,
  "decimal" numeric(10,2),
  "float4" float4,
  "float8" float8,
  "inet" inet,
  "interval" interval(6),
  "json" json,
  "jsonb" jsonb,
  "line" line,
  "lseg" lseg,
  "macaddr" macaddr,
  "money" money,
  "numeric" numeric,
  "path" path,
  "point" point,
  "polygon" polygon,
  "serial" serial,
  "serial2" serial2,
  "serial4" serial4,
  "serial8" serial8,
  "smallserial" smallserial,
  "text" text COLLATE "pg_catalog"."default",
  "time" time(6),
  "timestamp" timestamp(6),
  "timestamptz" timestamptz(6),
  "timetz" timetz(6),
  "tsquery" tsquery,
  "tsvector" tsvector,
  "txid_snapshot" txid_snapshot,
  "uuid" uuid,
  "varbit" varbit,
  "xml" xml DEFAULT ''::xml,
  "test-filed-name1" varchar(255) COLLATE "pg_catalog"."default",
  "test_filed_name2" varchar(255) COLLATE "pg_catalog"."default",
  "test-filed_name3" varchar(255) COLLATE "pg_catalog"."default",
  CONSTRAINT "test-table_pkey" PRIMARY KEY ("id")
);
ALTER TABLE "public"."test-table" 
  OWNER TO "postgres";
COMMENT ON COLUMN "public"."test-table"."id" IS '字段描述：主键id';
COMMENT ON COLUMN "public"."test-table"."int2" IS '字段描述：int2';
COMMENT ON COLUMN "public"."test-table"."int4" IS '字段描述：int4';
COMMENT ON COLUMN "public"."test-table"."int8" IS '字段描述：int8';
COMMENT ON COLUMN "public"."test-table"."bigserial" IS '字段描述：bigserial';
COMMENT ON COLUMN "public"."test-table"."bit" IS '字段描述：bit';
COMMENT ON COLUMN "public"."test-table"."bool" IS '字段描述：bool';
COMMENT ON COLUMN "public"."test-table"."box" IS '字段描述：box';
COMMENT ON COLUMN "public"."test-table"."bytea" IS '字段描述：bytea';
COMMENT ON COLUMN "public"."test-table"."char" IS '字段描述：char';
COMMENT ON COLUMN "public"."test-table"."cidr" IS '字段描述：cidr';
COMMENT ON COLUMN "public"."test-table"."circle" IS '字段描述：circle';
COMMENT ON COLUMN "public"."test-table"."date" IS '字段描述：date';
COMMENT ON COLUMN "public"."test-table"."decimal" IS '字段描述：decimal';
COMMENT ON COLUMN "public"."test-table"."float4" IS '字段描述：float4';
COMMENT ON COLUMN "public"."test-table"."float8" IS '字段描述：float8';
COMMENT ON COLUMN "public"."test-table"."inet" IS '字段描述：inet';
COMMENT ON COLUMN "public"."test-table"."interval" IS '字段描述：interval';
COMMENT ON COLUMN "public"."test-table"."json" IS '字段描述：json';
COMMENT ON COLUMN "public"."test-table"."jsonb" IS '字段描述：jsonb';
COMMENT ON COLUMN "public"."test-table"."line" IS '字段描述：line';
COMMENT ON COLUMN "public"."test-table"."lseg" IS '字段描述：lseg';
COMMENT ON COLUMN "public"."test-table"."macaddr" IS '字段描述：macaddr';
COMMENT ON COLUMN "public"."test-table"."money" IS '字段描述：money';
COMMENT ON COLUMN "public"."test-table"."numeric" IS '字段描述：numeric';
COMMENT ON COLUMN "public"."test-table"."path" IS '字段描述：path';
COMMENT ON COLUMN "public"."test-table"."point" IS '字段描述：point';
COMMENT ON COLUMN "public"."test-table"."polygon" IS '字段描述：polygon';
COMMENT ON COLUMN "public"."test-table"."serial" IS '字段描述：serial';
COMMENT ON COLUMN "public"."test-table"."serial2" IS '字段描述：serial2';
COMMENT ON COLUMN "public"."test-table"."serial4" IS '字段描述：serial4';
COMMENT ON COLUMN "public"."test-table"."serial8" IS '字段描述：serial8';
COMMENT ON COLUMN "public"."test-table"."smallserial" IS '字段描述：smallserial';
COMMENT ON COLUMN "public"."test-table"."text" IS '字段描述：text';
COMMENT ON COLUMN "public"."test-table"."time" IS '字段描述：time';
COMMENT ON COLUMN "public"."test-table"."timestamp" IS '字段描述：timestamp';
COMMENT ON COLUMN "public"."test-table"."timestamptz" IS '字段描述：timestamptz';
COMMENT ON COLUMN "public"."test-table"."timetz" IS '字段描述：timetz';
COMMENT ON COLUMN "public"."test-table"."tsquery" IS '字段描述：tsquery';
COMMENT ON COLUMN "public"."test-table"."tsvector" IS '字段描述：tsvector';
COMMENT ON COLUMN "public"."test-table"."txid_snapshot" IS '字段描述：txid_snapshot';
COMMENT ON COLUMN "public"."test-table"."uuid" IS '字段描述：uuid';
COMMENT ON COLUMN "public"."test-table"."varbit" IS '字段描述：varbit';
COMMENT ON COLUMN "public"."test-table"."xml" IS '字段描述：xml';
COMMENT ON COLUMN "public"."test-table"."test-filed-name1" IS '字段描述：test-filed-name1';
COMMENT ON COLUMN "public"."test-table"."test_filed_name2" IS '字段描述：test_filed_name2';
COMMENT ON COLUMN "public"."test-table"."test-filed_name3" IS '字段描述：test-filed_name3';
COMMENT ON TABLE "public"."test-table" IS '表描述';
```


| 变量项                         | 变量值                                       |
|-----------------------------|-------------------------------------------|
| `${settings.projectPath}`   | D:\workspace\houkunlin\Database Generator |
| `${settings.javaPath}`      | src/main/java1                            |
| `${settings.resourcesPath}` | src/main/resources                        |
| `${developer.author}`       | HouKunLin                                 |
| `${developer.email}`        | houkunlin@aliyun.com                      |

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

| 变量项                                             | 变量值                           |
|-------------------------------------------------|-------------------------------|
| `${date}`                                       | 2025-05-23T01:32:42.631+08:00 |
| `${date.toString("yyyy-MM-dd HH:mm:ss")}`       | 2025-05-23 01:32:42           |
| `${date.now().toString("yyyy-MM-dd HH:mm:ss")}` | 2025-05-23 01:32:42           |

## entity

| 变量项                 | 变量值        |
|---------------------|------------|
| `${entity.comment}` | 表描述        |
| `${entity.uri}`     | test-table |

### entity.name

| 变量项                          | 变量值                  |
|------------------------------|----------------------|
| `${entity.name}`             | TestTable            |
| `${entity.name.firstUpper}`  | TestTable            |
| `${entity.name.firstLower}`  | testTable            |
| `${entity.name.entity}`      | TestTableEntity      |
| `${entity.name.service}`     | TestTableService     |
| `${entity.name.serviceImpl}` | TestTableServiceImpl |
| `${entity.name.dao}`         | TestTableRepository  |
| `${entity.name.controller}`  | TestTableController  |

#### entity.name.entity

| 变量项                                | 变量值             |
|------------------------------------|-----------------|
| `${entity.name.entity}`            | TestTableEntity |
| `${entity.name.entity.firstUpper}` | TestTableEntity |
| `${entity.name.entity.firstLower}` | testTableEntity |

#### entity.name.service

| 变量项                                 | 变量值              |
|-------------------------------------|------------------|
| `${entity.name.service}`            | TestTableService |
| `${entity.name.service.firstUpper}` | TestTableService |
| `${entity.name.service.firstLower}` | testTableService |

#### entity.name.serviceImpl

| 变量项                                     | 变量值                  |
|-----------------------------------------|----------------------|
| `${entity.name.serviceImpl}`            | TestTableServiceImpl |
| `${entity.name.serviceImpl.firstUpper}` | TestTableServiceImpl |
| `${entity.name.serviceImpl.firstLower}` | testTableServiceImpl |

#### entity.name.dao

| 变量项                             | 变量值                 |
|---------------------------------|---------------------|
| `${entity.name.dao}`            | TestTableRepository |
| `${entity.name.dao.firstUpper}` | TestTableRepository |
| `${entity.name.dao.firstLower}` | testTableRepository |

#### entity.name.controller

| 变量项                                    | 变量值                 |
|----------------------------------------|---------------------|
| `${entity.name.controller}`            | TestTableController |
| `${entity.name.controller.firstUpper}` | TestTableController |
| `${entity.name.controller.firstLower}` | testTableController |

### entity.packages

```
// ${entity.packages} =

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

```

| 变量项                              | 变量值                      |
|----------------------------------|--------------------------|
| `${entity.packages.entity}`      | com.example.entity       |
| `${entity.packages.service}`     | com.example.service      |
| `${entity.packages.serviceImpl}` | com.example.service.impl |
| `${entity.packages.dao}`         | com.example.repository   |
| `${entity.packages.controller}`  | com.example.controller   |

#### entity.packages.entity

| 变量项                              | 变量值                                |
|----------------------------------|------------------------------------|
| `${entity.packages.entity}`      | com.example.entity                 |
| `${entity.packages.entity.pack}` | com.example.entity                 |
| `${entity.packages.entity.full}` | com.example.entity.TestTableEntity |

#### entity.packages.service

| 变量项                               | 变量值                                  |
|-----------------------------------|--------------------------------------|
| `${entity.packages.service}`      | com.example.service                  |
| `${entity.packages.service.pack}` | com.example.service                  |
| `${entity.packages.service.full}` | com.example.service.TestTableService |

#### entity.packages.serviceImpl

| 变量项                                   | 变量值                                           |
|---------------------------------------|-----------------------------------------------|
| `${entity.packages.serviceImpl}`      | com.example.service.impl                      |
| `${entity.packages.serviceImpl.pack}` | com.example.service.impl                      |
| `${entity.packages.serviceImpl.full}` | com.example.service.impl.TestTableServiceImpl |

#### entity.packages.dao

| 变量项                           | 变量值                                        |
|-------------------------------|--------------------------------------------|
| `${entity.packages.dao}`      | com.example.repository                     |
| `${entity.packages.dao.pack}` | com.example.repository                     |
| `${entity.packages.dao.full}` | com.example.repository.TestTableRepository |

#### entity.packages.controller

| 变量项                                  | 变量值                                        |
|--------------------------------------|--------------------------------------------|
| `${entity.packages.controller}`      | com.example.controller                     |
| `${entity.packages.controller.pack}` | com.example.controller                     |
| `${entity.packages.controller.full}` | com.example.controller.TestTableController |

## table

| 变量项                | 变量值        |
|--------------------|------------|
| `${table.name}`    | test-table |
| `${table.comment}` | 表描述        |

## primary.field

| 变量项                                                | 变量值               |
|----------------------------------------------------|-------------------|
| `${primary.field.name}`                            | id                |
| `${primary.field.name.firstUpper}`                 | Id                |
| `${primary.field.name.firstLower}`                 | id                |
| `${primary.field.comment}`                         | 字段描述：主键id         |
| `${primary.field.typeName}`                        | Integer           |
| `${(primary.field.dataType.length)!'null'}`        | -1                |
| `${(primary.field.dataType.precision)!'null'}`     | -1                |
| `${(primary.field.dataType.scale)!'null'}`         | 0                 |
| `${(primary.field.dataType.specification)!'null'}` | smallint          |
| `${primary.field.fullTypeName}`                    | java.lang.Integer |
| `${primary.field.primaryKey}`                      | true              |
| `${primary.field.selected}`                        | true              |

## primary.column

| 变量项                                                 | 变量值       |
|-----------------------------------------------------|-----------|
| `${primary.column.name}`                            | id        |
| `${primary.column.comment}`                         | 字段描述：主键id |
| `${primary.column.typeName}`                        | smallint  |
| `${(primary.column.dataType.length)!'null'}`        | -1        |
| `${(primary.column.dataType.precision)!'null'}`     | -1        |
| `${(primary.column.dataType.scale)!'null'}`         | 0         |
| `${(primary.column.dataType.specification)!'null'}` | smallint  |
| `${primary.column.fullTypeName}`                    | smallint  |
| `${primary.column.primaryKey}`                      | true      |
| `${primary.column.selected}`                        | true      |

## <#list primary.fields as field>

```
<#list primary.fields as field>
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
</#list>
```

| 序号 | `${field.name}` | `${field.name.firstUpper}` | `${field.name.firstLower}` | `${field.comment}` | `${field.typeName}` | `${field.fullTypeName}` | `${field.dataType.length}` | `${field.dataType.precision}` | `${field.dataType.scale}` | `${field.dataType.specification}` | `${field.primaryKey}` | `${field.selected}` |
|----|-----------------|----------------------------|----------------------------|--------------------|---------------------|-------------------------|----------------------------|-------------------------------|---------------------------|-----------------------------------|-----------------------|---------------------|
| 1  | id              | Id                         | id                         | 字段描述：主键id          | Integer             | java.lang.Integer       | -1                         | -1                            | 0                         | smallint                          | true                  | true                |

## <#list primary.columns as column>

```
<#list primary.columns as column>
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
</#list>
```

| 序号 | `${column.name}` | `${column.comment}` | `${column.typeName}` | `${column.fullTypeName}` | `${column.dataType.length}` | `${column.dataType.precision}` | `${column.dataType.scale}` | `${column.dataType.specification}` | `${column.primaryKey}` | `${column.selected}` |
|----|------------------|---------------------|----------------------|--------------------------|-----------------------------|--------------------------------|----------------------------|------------------------------------|------------------------|----------------------|
| 1  | id               | 字段描述：主键id           | smallint             | smallint                 | -1                          | -1                             | 0                          | smallint                           | true                   | true                 |

## <#list fields as field>

### field 用法

```
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
</#list>
```

| 序号 | `${field.name}`  | `${field.name.firstUpper}` | `${field.name.firstLower}` | `${field.comment}`    | `${field.typeName}` | `${field.fullTypeName}` | `${field.dataType.length}` | `${field.dataType.precision}` | `${field.dataType.scale}` | `${field.dataType.specification}` | `${field.primaryKey}` | `${field.selected}` |
|----|------------------|----------------------------|----------------------------|-----------------------|---------------------|-------------------------|----------------------------|-------------------------------|---------------------------|-----------------------------------|-----------------------|---------------------|
| 1  | id               | Id                         | id                         | 字段描述：主键id             | Integer             | java.lang.Integer       | -1                         | -1                            | 0                         | smallint                          | true                  | true                |
| 2  | int2             | Int2                       | int2                       | 字段描述：int2             | Integer             | java.lang.Integer       | -1                         | -1                            | 0                         | smallint                          | false                 | true                |
| 3  | int4             | Int4                       | int4                       | 字段描述：int4             | Integer             | java.lang.Integer       | -1                         | -1                            | 0                         | integer                           | false                 | true                |
| 4  | int8             | Int8                       | int8                       | 字段描述：int8             | Long                | java.lang.Long          | -1                         | -1                            | 0                         | bigint                            | false                 | true                |
| 5  | bigserial        | Bigserial                  | bigserial                  | 字段描述：bigserial        | Long                | java.lang.Long          | -1                         | -1                            | 0                         | bigint                            | false                 | true                |
| 6  | bit              | Bit                        | bit                        | 字段描述：bit              | Boolean             | java.lang.Boolean       | -1                         | -1                            | 0                         | bit                               | false                 | true                |
| 7  | bool             | Bool                       | bool                       | 字段描述：bool             | Boolean             | java.lang.Boolean       | -1                         | -1                            | 0                         | boolean                           | false                 | true                |
| 8  | box              | Box                        | box                        | 字段描述：box              | Object              | java.lang.Object        | -1                         | -1                            | 0                         | box                               | false                 | true                |
| 9  | bytea            | Bytea                      | bytea                      | 字段描述：bytea            | Object              | java.lang.Object        | -1                         | -1                            | 0                         | bytea                             | false                 | true                |
| 10 | char             | Char                       | char                       | 字段描述：char             | String              | java.lang.String        | -1                         | -1                            | 0                         | char                              | false                 | true                |
| 11 | cidr             | Cidr                       | cidr                       | 字段描述：cidr             | String              | java.lang.String        | -1                         | -1                            | 0                         | cidr                              | false                 | true                |
| 12 | circle           | Circle                     | circle                     | 字段描述：circle           | Object              | java.lang.Object        | -1                         | -1                            | 0                         | circle                            | false                 | true                |
| 13 | date             | Date                       | date                       | 字段描述：date             | LocalDate           | java.time.LocalDate     | -1                         | -1                            | 0                         | date                              | false                 | true                |
| 14 | decimal          | Decimal                    | decimal                    | 字段描述：decimal          | BigDecimal          | java.math.BigDecimal    | 10                         | 10                            | 2                         | numeric(10,2)                     | false                 | true                |
| 15 | float4           | Float4                     | float4                     | 字段描述：float4           | Float               | java.lang.Float         | -1                         | -1                            | 0                         | real                              | false                 | true                |
| 16 | float8           | Float8                     | float8                     | 字段描述：float8           | Double              | java.lang.Double        | -1                         | -1                            | 0                         | double precision                  | false                 | true                |
| 17 | inet             | Inet                       | inet                       | 字段描述：inet             | String              | java.lang.String        | -1                         | -1                            | 0                         | inet                              | false                 | true                |
| 18 | interval         | Interval                   | interval                   | 字段描述：interval         | Integer             | java.lang.Integer       | -1                         | -1                            | 0                         | interval                          | false                 | true                |
| 19 | json             | Json                       | json                       | 字段描述：json             | Object              | java.lang.Object        | -1                         | -1                            | 0                         | json                              | false                 | true                |
| 20 | jsonb            | Jsonb                      | jsonb                      | 字段描述：jsonb            | Object              | java.lang.Object        | -1                         | -1                            | 0                         | jsonb                             | false                 | true                |
| 21 | line             | Line                       | line                       | 字段描述：line             | Object              | java.lang.Object        | -1                         | -1                            | 0                         | line                              | false                 | true                |
| 22 | lseg             | Lseg                       | lseg                       | 字段描述：lseg             | Object              | java.lang.Object        | -1                         | -1                            | 0                         | lseg                              | false                 | true                |
| 23 | macaddr          | Macaddr                    | macaddr                    | 字段描述：macaddr          | String              | java.lang.String        | -1                         | -1                            | 0                         | macaddr                           | false                 | true                |
| 24 | money            | Money                      | money                      | 字段描述：money            | BigDecimal          | java.math.BigDecimal    | -1                         | -1                            | 0                         | money                             | false                 | true                |
| 25 | numeric          | Numeric                    | numeric                    | 字段描述：numeric          | BigDecimal          | java.math.BigDecimal    | -1                         | -1                            | 0                         | numeric                           | false                 | true                |
| 26 | path             | Path                       | path                       | 字段描述：path             | String              | java.lang.String        | -1                         | -1                            | 0                         | path                              | false                 | true                |
| 27 | point            | Point                      | point                      | 字段描述：point            | Object              | java.lang.Object        | -1                         | -1                            | 0                         | point                             | false                 | true                |
| 28 | polygon          | Polygon                    | polygon                    | 字段描述：polygon          | Object              | java.lang.Object        | -1                         | -1                            | 0                         | polygon                           | false                 | true                |
| 29 | serial           | Serial                     | serial                     | 字段描述：serial           | Integer             | java.lang.Integer       | -1                         | -1                            | 0                         | integer                           | false                 | true                |
| 30 | serial2          | Serial2                    | serial2                    | 字段描述：serial2          | Integer             | java.lang.Integer       | -1                         | -1                            | 0                         | smallint                          | false                 | true                |
| 31 | serial4          | Serial4                    | serial4                    | 字段描述：serial4          | Integer             | java.lang.Integer       | -1                         | -1                            | 0                         | integer                           | false                 | true                |
| 32 | serial8          | Serial8                    | serial8                    | 字段描述：serial8          | Long                | java.lang.Long          | -1                         | -1                            | 0                         | bigint                            | false                 | true                |
| 33 | smallserial      | Smallserial                | smallserial                | 字段描述：smallserial      | Integer             | java.lang.Integer       | -1                         | -1                            | 0                         | smallint                          | false                 | true                |
| 34 | text             | Text                       | text                       | 字段描述：text             | String              | java.lang.String        | -1                         | -1                            | 0                         | text                              | false                 | true                |
| 35 | time             | Time                       | time                       | 字段描述：time             | LocalTime           | java.time.LocalTime     | -1                         | -1                            | 0                         | time                              | false                 | true                |
| 36 | timestamp        | Timestamp                  | timestamp                  | 字段描述：timestamp        | LocalDateTime       | java.time.LocalDateTime | -1                         | -1                            | 0                         | timestamp                         | false                 | true                |
| 37 | timestamptz      | Timestamptz                | timestamptz                | 字段描述：timestamptz      | LocalDateTime       | java.time.LocalDateTime | -1                         | -1                            | 0                         | timestamp with time zone          | false                 | true                |
| 38 | timetz           | Timetz                     | timetz                     | 字段描述：timetz           | LocalTime           | java.time.LocalTime     | -1                         | -1                            | 0                         | time with time zone               | false                 | true                |
| 39 | tsquery          | Tsquery                    | tsquery                    | 字段描述：tsquery          | Object              | java.lang.Object        | -1                         | -1                            | 0                         | tsquery                           | false                 | true                |
| 40 | tsvector         | Tsvector                   | tsvector                   | 字段描述：tsvector         | Object              | java.lang.Object        | -1                         | -1                            | 0                         | tsvector                          | false                 | true                |
| 41 | txid_snapshot    | Txid_snapshot              | txid_snapshot              | 字段描述：txid_snapshot    | Object              | java.lang.Object        | -1                         | -1                            | 0                         | txid_snapshot                     | false                 | true                |
| 42 | uuid             | Uuid                       | uuid                       | 字段描述：uuid             | String              | java.lang.String        | -1                         | -1                            | 0                         | uuid                              | false                 | true                |
| 43 | varbit           | Varbit                     | varbit                     | 字段描述：varbit           | Object              | java.lang.Object        | -1                         | -1                            | 0                         | bit varying                       | false                 | true                |
| 44 | xml              | Xml                        | xml                        | 字段描述：xml              | String              | java.lang.String        | -1                         | -1                            | 0                         | xml                               | false                 | true                |
| 45 | testFiledName1   | TestFiledName1             | testFiledName1             | 字段描述：test-filed-name1 | String              | java.lang.String        | 255                        | 255                           | 0                         | varchar(255)                      | false                 | true                |
| 46 | test_filed_name2 | Test_filed_name2           | test_filed_name2           | 字段描述：test_filed_name2 | String              | java.lang.String        | 255                        | 255                           | 0                         | varchar(255)                      | false                 | true                |
| 47 | testFiled_name3  | TestFiled_name3            | testFiled_name3            | 字段描述：test-filed_name3 | String              | java.lang.String        | 255                        | 255                           | 0                         | varchar(255)                      | false                 | true                |

### field.column 用法

```
<#list fields as field>
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
</#list>
```

| 序号 | `${field.column.name}` | `${field.column.comment}` | `${field.column.typeName}` | `${field.column.fullTypeName}` | `${field.column.dataType.length}` | `${field.column.dataType.precision}` | `${field.column.dataType.scale}` | `${field.column.dataType.specification}` | `${field.column.primaryKey}` | `${field.column.selected}` |
|----|------------------------|---------------------------|----------------------------|--------------------------------|-----------------------------------|--------------------------------------|----------------------------------|------------------------------------------|------------------------------|----------------------------|
| 1  | id                     | 字段描述：主键id                 | smallint                   | smallint                       | -1                                | -1                                   | 0                                | smallint                                 | true                         | true                       |
| 2  | int2                   | 字段描述：int2                 | smallint                   | smallint                       | -1                                | -1                                   | 0                                | smallint                                 | false                        | true                       |
| 3  | int4                   | 字段描述：int4                 | integer                    | integer                        | -1                                | -1                                   | 0                                | integer                                  | false                        | true                       |
| 4  | int8                   | 字段描述：int8                 | bigint                     | bigint                         | -1                                | -1                                   | 0                                | bigint                                   | false                        | true                       |
| 5  | bigserial              | 字段描述：bigserial            | bigint                     | bigint                         | -1                                | -1                                   | 0                                | bigint                                   | false                        | true                       |
| 6  | bit                    | 字段描述：bit                  | bit                        | bit                            | -1                                | -1                                   | 0                                | bit                                      | false                        | true                       |
| 7  | bool                   | 字段描述：bool                 | boolean                    | boolean                        | -1                                | -1                                   | 0                                | boolean                                  | false                        | true                       |
| 8  | box                    | 字段描述：box                  | box                        | box                            | -1                                | -1                                   | 0                                | box                                      | false                        | true                       |
| 9  | bytea                  | 字段描述：bytea                | bytea                      | bytea                          | -1                                | -1                                   | 0                                | bytea                                    | false                        | true                       |
| 10 | char                   | 字段描述：char                 | char                       | char                           | -1                                | -1                                   | 0                                | char                                     | false                        | true                       |
| 11 | cidr                   | 字段描述：cidr                 | cidr                       | cidr                           | -1                                | -1                                   | 0                                | cidr                                     | false                        | true                       |
| 12 | circle                 | 字段描述：circle               | circle                     | circle                         | -1                                | -1                                   | 0                                | circle                                   | false                        | true                       |
| 13 | date                   | 字段描述：date                 | date                       | date                           | -1                                | -1                                   | 0                                | date                                     | false                        | true                       |
| 14 | decimal                | 字段描述：decimal              | numeric                    | numeric(10,2)                  | 10                                | 10                                   | 2                                | numeric(10,2)                            | false                        | true                       |
| 15 | float4                 | 字段描述：float4               | real                       | real                           | -1                                | -1                                   | 0                                | real                                     | false                        | true                       |
| 16 | float8                 | 字段描述：float8               | double precision           | double precision               | -1                                | -1                                   | 0                                | double precision                         | false                        | true                       |
| 17 | inet                   | 字段描述：inet                 | inet                       | inet                           | -1                                | -1                                   | 0                                | inet                                     | false                        | true                       |
| 18 | interval               | 字段描述：interval             | interval                   | interval                       | -1                                | -1                                   | 0                                | interval                                 | false                        | true                       |
| 19 | json                   | 字段描述：json                 | json                       | json                           | -1                                | -1                                   | 0                                | json                                     | false                        | true                       |
| 20 | jsonb                  | 字段描述：jsonb                | jsonb                      | jsonb                          | -1                                | -1                                   | 0                                | jsonb                                    | false                        | true                       |
| 21 | line                   | 字段描述：line                 | line                       | line                           | -1                                | -1                                   | 0                                | line                                     | false                        | true                       |
| 22 | lseg                   | 字段描述：lseg                 | lseg                       | lseg                           | -1                                | -1                                   | 0                                | lseg                                     | false                        | true                       |
| 23 | macaddr                | 字段描述：macaddr              | macaddr                    | macaddr                        | -1                                | -1                                   | 0                                | macaddr                                  | false                        | true                       |
| 24 | money                  | 字段描述：money                | money                      | money                          | -1                                | -1                                   | 0                                | money                                    | false                        | true                       |
| 25 | numeric                | 字段描述：numeric              | numeric                    | numeric                        | -1                                | -1                                   | 0                                | numeric                                  | false                        | true                       |
| 26 | path                   | 字段描述：path                 | path                       | path                           | -1                                | -1                                   | 0                                | path                                     | false                        | true                       |
| 27 | point                  | 字段描述：point                | point                      | point                          | -1                                | -1                                   | 0                                | point                                    | false                        | true                       |
| 28 | polygon                | 字段描述：polygon              | polygon                    | polygon                        | -1                                | -1                                   | 0                                | polygon                                  | false                        | true                       |
| 29 | serial                 | 字段描述：serial               | integer                    | integer                        | -1                                | -1                                   | 0                                | integer                                  | false                        | true                       |
| 30 | serial2                | 字段描述：serial2              | smallint                   | smallint                       | -1                                | -1                                   | 0                                | smallint                                 | false                        | true                       |
| 31 | serial4                | 字段描述：serial4              | integer                    | integer                        | -1                                | -1                                   | 0                                | integer                                  | false                        | true                       |
| 32 | serial8                | 字段描述：serial8              | bigint                     | bigint                         | -1                                | -1                                   | 0                                | bigint                                   | false                        | true                       |
| 33 | smallserial            | 字段描述：smallserial          | smallint                   | smallint                       | -1                                | -1                                   | 0                                | smallint                                 | false                        | true                       |
| 34 | text                   | 字段描述：text                 | text                       | text                           | -1                                | -1                                   | 0                                | text                                     | false                        | true                       |
| 35 | time                   | 字段描述：time                 | time                       | time                           | -1                                | -1                                   | 0                                | time                                     | false                        | true                       |
| 36 | timestamp              | 字段描述：timestamp            | timestamp                  | timestamp                      | -1                                | -1                                   | 0                                | timestamp                                | false                        | true                       |
| 37 | timestamptz            | 字段描述：timestamptz          | timestamp with time zone   | timestamp with time zone       | -1                                | -1                                   | 0                                | timestamp with time zone                 | false                        | true                       |
| 38 | timetz                 | 字段描述：timetz               | time with time zone        | time with time zone            | -1                                | -1                                   | 0                                | time with time zone                      | false                        | true                       |
| 39 | tsquery                | 字段描述：tsquery              | tsquery                    | tsquery                        | -1                                | -1                                   | 0                                | tsquery                                  | false                        | true                       |
| 40 | tsvector               | 字段描述：tsvector             | tsvector                   | tsvector                       | -1                                | -1                                   | 0                                | tsvector                                 | false                        | true                       |
| 41 | txid_snapshot          | 字段描述：txid_snapshot        | txid_snapshot              | txid_snapshot                  | -1                                | -1                                   | 0                                | txid_snapshot                            | false                        | true                       |
| 42 | uuid                   | 字段描述：uuid                 | uuid                       | uuid                           | -1                                | -1                                   | 0                                | uuid                                     | false                        | true                       |
| 43 | varbit                 | 字段描述：varbit               | bit varying                | bit varying                    | -1                                | -1                                   | 0                                | bit varying                              | false                        | true                       |
| 44 | xml                    | 字段描述：xml                  | xml                        | xml                            | -1                                | -1                                   | 0                                | xml                                      | false                        | true                       |
| 45 | test-filed-name1       | 字段描述：test-filed-name1     | varchar                    | varchar(255)                   | 255                               | 255                                  | 0                                | varchar(255)                             | false                        | true                       |
| 46 | test_filed_name2       | 字段描述：test_filed_name2     | varchar                    | varchar(255)                   | 255                               | 255                                  | 0                                | varchar(255)                             | false                        | true                       |
| 47 | test-filed_name3       | 字段描述：test-filed_name3     | varchar                    | varchar(255)                   | 255                               | 255                                  | 0                                | varchar(255)                             | false                        | true                       |

## <#list columns as column>

### column 用法

```
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
</#list>
```

| 序号 | `${column.name}` | `${column.comment}`   | `${column.typeName}`     | `${column.fullTypeName}` | `${column.dataType.length}` | `${column.dataType.precision}` | `${column.dataType.scale}` | `${column.dataType.specification}` | `${column.primaryKey}` | `${column.selected}` |
|----|------------------|-----------------------|--------------------------|--------------------------|-----------------------------|--------------------------------|----------------------------|------------------------------------|------------------------|----------------------|
| 1  | id               | 字段描述：主键id             | smallint                 | smallint                 | -1                          | -1                             | 0                          | smallint                           | true                   | true                 |
| 2  | int2             | 字段描述：int2             | smallint                 | smallint                 | -1                          | -1                             | 0                          | smallint                           | false                  | true                 |
| 3  | int4             | 字段描述：int4             | integer                  | integer                  | -1                          | -1                             | 0                          | integer                            | false                  | true                 |
| 4  | int8             | 字段描述：int8             | bigint                   | bigint                   | -1                          | -1                             | 0                          | bigint                             | false                  | true                 |
| 5  | bigserial        | 字段描述：bigserial        | bigint                   | bigint                   | -1                          | -1                             | 0                          | bigint                             | false                  | true                 |
| 6  | bit              | 字段描述：bit              | bit                      | bit                      | -1                          | -1                             | 0                          | bit                                | false                  | true                 |
| 7  | bool             | 字段描述：bool             | boolean                  | boolean                  | -1                          | -1                             | 0                          | boolean                            | false                  | true                 |
| 8  | box              | 字段描述：box              | box                      | box                      | -1                          | -1                             | 0                          | box                                | false                  | true                 |
| 9  | bytea            | 字段描述：bytea            | bytea                    | bytea                    | -1                          | -1                             | 0                          | bytea                              | false                  | true                 |
| 10 | char             | 字段描述：char             | char                     | char                     | -1                          | -1                             | 0                          | char                               | false                  | true                 |
| 11 | cidr             | 字段描述：cidr             | cidr                     | cidr                     | -1                          | -1                             | 0                          | cidr                               | false                  | true                 |
| 12 | circle           | 字段描述：circle           | circle                   | circle                   | -1                          | -1                             | 0                          | circle                             | false                  | true                 |
| 13 | date             | 字段描述：date             | date                     | date                     | -1                          | -1                             | 0                          | date                               | false                  | true                 |
| 14 | decimal          | 字段描述：decimal          | numeric                  | numeric(10,2)            | 10                          | 10                             | 2                          | numeric(10,2)                      | false                  | true                 |
| 15 | float4           | 字段描述：float4           | real                     | real                     | -1                          | -1                             | 0                          | real                               | false                  | true                 |
| 16 | float8           | 字段描述：float8           | double precision         | double precision         | -1                          | -1                             | 0                          | double precision                   | false                  | true                 |
| 17 | inet             | 字段描述：inet             | inet                     | inet                     | -1                          | -1                             | 0                          | inet                               | false                  | true                 |
| 18 | interval         | 字段描述：interval         | interval                 | interval                 | -1                          | -1                             | 0                          | interval                           | false                  | true                 |
| 19 | json             | 字段描述：json             | json                     | json                     | -1                          | -1                             | 0                          | json                               | false                  | true                 |
| 20 | jsonb            | 字段描述：jsonb            | jsonb                    | jsonb                    | -1                          | -1                             | 0                          | jsonb                              | false                  | true                 |
| 21 | line             | 字段描述：line             | line                     | line                     | -1                          | -1                             | 0                          | line                               | false                  | true                 |
| 22 | lseg             | 字段描述：lseg             | lseg                     | lseg                     | -1                          | -1                             | 0                          | lseg                               | false                  | true                 |
| 23 | macaddr          | 字段描述：macaddr          | macaddr                  | macaddr                  | -1                          | -1                             | 0                          | macaddr                            | false                  | true                 |
| 24 | money            | 字段描述：money            | money                    | money                    | -1                          | -1                             | 0                          | money                              | false                  | true                 |
| 25 | numeric          | 字段描述：numeric          | numeric                  | numeric                  | -1                          | -1                             | 0                          | numeric                            | false                  | true                 |
| 26 | path             | 字段描述：path             | path                     | path                     | -1                          | -1                             | 0                          | path                               | false                  | true                 |
| 27 | point            | 字段描述：point            | point                    | point                    | -1                          | -1                             | 0                          | point                              | false                  | true                 |
| 28 | polygon          | 字段描述：polygon          | polygon                  | polygon                  | -1                          | -1                             | 0                          | polygon                            | false                  | true                 |
| 29 | serial           | 字段描述：serial           | integer                  | integer                  | -1                          | -1                             | 0                          | integer                            | false                  | true                 |
| 30 | serial2          | 字段描述：serial2          | smallint                 | smallint                 | -1                          | -1                             | 0                          | smallint                           | false                  | true                 |
| 31 | serial4          | 字段描述：serial4          | integer                  | integer                  | -1                          | -1                             | 0                          | integer                            | false                  | true                 |
| 32 | serial8          | 字段描述：serial8          | bigint                   | bigint                   | -1                          | -1                             | 0                          | bigint                             | false                  | true                 |
| 33 | smallserial      | 字段描述：smallserial      | smallint                 | smallint                 | -1                          | -1                             | 0                          | smallint                           | false                  | true                 |
| 34 | text             | 字段描述：text             | text                     | text                     | -1                          | -1                             | 0                          | text                               | false                  | true                 |
| 35 | time             | 字段描述：time             | time                     | time                     | -1                          | -1                             | 0                          | time                               | false                  | true                 |
| 36 | timestamp        | 字段描述：timestamp        | timestamp                | timestamp                | -1                          | -1                             | 0                          | timestamp                          | false                  | true                 |
| 37 | timestamptz      | 字段描述：timestamptz      | timestamp with time zone | timestamp with time zone | -1                          | -1                             | 0                          | timestamp with time zone           | false                  | true                 |
| 38 | timetz           | 字段描述：timetz           | time with time zone      | time with time zone      | -1                          | -1                             | 0                          | time with time zone                | false                  | true                 |
| 39 | tsquery          | 字段描述：tsquery          | tsquery                  | tsquery                  | -1                          | -1                             | 0                          | tsquery                            | false                  | true                 |
| 40 | tsvector         | 字段描述：tsvector         | tsvector                 | tsvector                 | -1                          | -1                             | 0                          | tsvector                           | false                  | true                 |
| 41 | txid_snapshot    | 字段描述：txid_snapshot    | txid_snapshot            | txid_snapshot            | -1                          | -1                             | 0                          | txid_snapshot                      | false                  | true                 |
| 42 | uuid             | 字段描述：uuid             | uuid                     | uuid                     | -1                          | -1                             | 0                          | uuid                               | false                  | true                 |
| 43 | varbit           | 字段描述：varbit           | bit varying              | bit varying              | -1                          | -1                             | 0                          | bit varying                        | false                  | true                 |
| 44 | xml              | 字段描述：xml              | xml                      | xml                      | -1                          | -1                             | 0                          | xml                                | false                  | true                 |
| 45 | test-filed-name1 | 字段描述：test-filed-name1 | varchar                  | varchar(255)             | 255                         | 255                            | 0                          | varchar(255)                       | false                  | true                 |
| 46 | test_filed_name2 | 字段描述：test_filed_name2 | varchar                  | varchar(255)             | 255                         | 255                            | 0                          | varchar(255)                       | false                  | true                 |
| 47 | test-filed_name3 | 字段描述：test-filed_name3 | varchar                  | varchar(255)             | 255                         | 255                            | 0                          | varchar(255)                       | false                  | true                 |

### column.field 用法

```
<#list columns as column>
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
</#list>
```

| 序号 | `${column.field.name}` | `${column.field.name.firstUpper}` | `${column.field.name.firstLower}` | `${column.field.comment}` | `${column.field.typeName}` | `${column.field.fullTypeName}` | `${column.field.dataType.length}` | `${column.field.dataType.precision}` | `${column.field.dataType.scale}` | `${column.dataType.specification}` | `${column.field.primaryKey}` | `${column.field.selected}` |
|----|------------------------|-----------------------------------|-----------------------------------|---------------------------|----------------------------|--------------------------------|-----------------------------------|--------------------------------------|----------------------------------|------------------------------------|------------------------------|----------------------------|
| 1  | id                     | Id                                | id                                | 字段描述：主键id                 | Integer                    | java.lang.Integer              | -1                                | -1                                   | 0                                | smallint                           | true                         | true                       |
| 2  | int2                   | Int2                              | int2                              | 字段描述：int2                 | Integer                    | java.lang.Integer              | -1                                | -1                                   | 0                                | smallint                           | false                        | true                       |
| 3  | int4                   | Int4                              | int4                              | 字段描述：int4                 | Integer                    | java.lang.Integer              | -1                                | -1                                   | 0                                | integer                            | false                        | true                       |
| 4  | int8                   | Int8                              | int8                              | 字段描述：int8                 | Long                       | java.lang.Long                 | -1                                | -1                                   | 0                                | bigint                             | false                        | true                       |
| 5  | bigserial              | Bigserial                         | bigserial                         | 字段描述：bigserial            | Long                       | java.lang.Long                 | -1                                | -1                                   | 0                                | bigint                             | false                        | true                       |
| 6  | bit                    | Bit                               | bit                               | 字段描述：bit                  | Boolean                    | java.lang.Boolean              | -1                                | -1                                   | 0                                | bit                                | false                        | true                       |
| 7  | bool                   | Bool                              | bool                              | 字段描述：bool                 | Boolean                    | java.lang.Boolean              | -1                                | -1                                   | 0                                | boolean                            | false                        | true                       |
| 8  | box                    | Box                               | box                               | 字段描述：box                  | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | box                                | false                        | true                       |
| 9  | bytea                  | Bytea                             | bytea                             | 字段描述：bytea                | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | bytea                              | false                        | true                       |
| 10 | char                   | Char                              | char                              | 字段描述：char                 | String                     | java.lang.String               | -1                                | -1                                   | 0                                | char                               | false                        | true                       |
| 11 | cidr                   | Cidr                              | cidr                              | 字段描述：cidr                 | String                     | java.lang.String               | -1                                | -1                                   | 0                                | cidr                               | false                        | true                       |
| 12 | circle                 | Circle                            | circle                            | 字段描述：circle               | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | circle                             | false                        | true                       |
| 13 | date                   | Date                              | date                              | 字段描述：date                 | LocalDate                  | java.time.LocalDate            | -1                                | -1                                   | 0                                | date                               | false                        | true                       |
| 14 | decimal                | Decimal                           | decimal                           | 字段描述：decimal              | BigDecimal                 | java.math.BigDecimal           | 10                                | 10                                   | 2                                | numeric(10,2)                      | false                        | true                       |
| 15 | float4                 | Float4                            | float4                            | 字段描述：float4               | Float                      | java.lang.Float                | -1                                | -1                                   | 0                                | real                               | false                        | true                       |
| 16 | float8                 | Float8                            | float8                            | 字段描述：float8               | Double                     | java.lang.Double               | -1                                | -1                                   | 0                                | double precision                   | false                        | true                       |
| 17 | inet                   | Inet                              | inet                              | 字段描述：inet                 | String                     | java.lang.String               | -1                                | -1                                   | 0                                | inet                               | false                        | true                       |
| 18 | interval               | Interval                          | interval                          | 字段描述：interval             | Integer                    | java.lang.Integer              | -1                                | -1                                   | 0                                | interval                           | false                        | true                       |
| 19 | json                   | Json                              | json                              | 字段描述：json                 | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | json                               | false                        | true                       |
| 20 | jsonb                  | Jsonb                             | jsonb                             | 字段描述：jsonb                | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | jsonb                              | false                        | true                       |
| 21 | line                   | Line                              | line                              | 字段描述：line                 | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | line                               | false                        | true                       |
| 22 | lseg                   | Lseg                              | lseg                              | 字段描述：lseg                 | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | lseg                               | false                        | true                       |
| 23 | macaddr                | Macaddr                           | macaddr                           | 字段描述：macaddr              | String                     | java.lang.String               | -1                                | -1                                   | 0                                | macaddr                            | false                        | true                       |
| 24 | money                  | Money                             | money                             | 字段描述：money                | BigDecimal                 | java.math.BigDecimal           | -1                                | -1                                   | 0                                | money                              | false                        | true                       |
| 25 | numeric                | Numeric                           | numeric                           | 字段描述：numeric              | BigDecimal                 | java.math.BigDecimal           | -1                                | -1                                   | 0                                | numeric                            | false                        | true                       |
| 26 | path                   | Path                              | path                              | 字段描述：path                 | String                     | java.lang.String               | -1                                | -1                                   | 0                                | path                               | false                        | true                       |
| 27 | point                  | Point                             | point                             | 字段描述：point                | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | point                              | false                        | true                       |
| 28 | polygon                | Polygon                           | polygon                           | 字段描述：polygon              | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | polygon                            | false                        | true                       |
| 29 | serial                 | Serial                            | serial                            | 字段描述：serial               | Integer                    | java.lang.Integer              | -1                                | -1                                   | 0                                | integer                            | false                        | true                       |
| 30 | serial2                | Serial2                           | serial2                           | 字段描述：serial2              | Integer                    | java.lang.Integer              | -1                                | -1                                   | 0                                | smallint                           | false                        | true                       |
| 31 | serial4                | Serial4                           | serial4                           | 字段描述：serial4              | Integer                    | java.lang.Integer              | -1                                | -1                                   | 0                                | integer                            | false                        | true                       |
| 32 | serial8                | Serial8                           | serial8                           | 字段描述：serial8              | Long                       | java.lang.Long                 | -1                                | -1                                   | 0                                | bigint                             | false                        | true                       |
| 33 | smallserial            | Smallserial                       | smallserial                       | 字段描述：smallserial          | Integer                    | java.lang.Integer              | -1                                | -1                                   | 0                                | smallint                           | false                        | true                       |
| 34 | text                   | Text                              | text                              | 字段描述：text                 | String                     | java.lang.String               | -1                                | -1                                   | 0                                | text                               | false                        | true                       |
| 35 | time                   | Time                              | time                              | 字段描述：time                 | LocalTime                  | java.time.LocalTime            | -1                                | -1                                   | 0                                | time                               | false                        | true                       |
| 36 | timestamp              | Timestamp                         | timestamp                         | 字段描述：timestamp            | LocalDateTime              | java.time.LocalDateTime        | -1                                | -1                                   | 0                                | timestamp                          | false                        | true                       |
| 37 | timestamptz            | Timestamptz                       | timestamptz                       | 字段描述：timestamptz          | LocalDateTime              | java.time.LocalDateTime        | -1                                | -1                                   | 0                                | timestamp with time zone           | false                        | true                       |
| 38 | timetz                 | Timetz                            | timetz                            | 字段描述：timetz               | LocalTime                  | java.time.LocalTime            | -1                                | -1                                   | 0                                | time with time zone                | false                        | true                       |
| 39 | tsquery                | Tsquery                           | tsquery                           | 字段描述：tsquery              | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | tsquery                            | false                        | true                       |
| 40 | tsvector               | Tsvector                          | tsvector                          | 字段描述：tsvector             | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | tsvector                           | false                        | true                       |
| 41 | txid_snapshot          | Txid_snapshot                     | txid_snapshot                     | 字段描述：txid_snapshot        | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | txid_snapshot                      | false                        | true                       |
| 42 | uuid                   | Uuid                              | uuid                              | 字段描述：uuid                 | String                     | java.lang.String               | -1                                | -1                                   | 0                                | uuid                               | false                        | true                       |
| 43 | varbit                 | Varbit                            | varbit                            | 字段描述：varbit               | Object                     | java.lang.Object               | -1                                | -1                                   | 0                                | bit varying                        | false                        | true                       |
| 44 | xml                    | Xml                               | xml                               | 字段描述：xml                  | String                     | java.lang.String               | -1                                | -1                                   | 0                                | xml                                | false                        | true                       |
| 45 | testFiledName1         | TestFiledName1                    | testFiledName1                    | 字段描述：test-filed-name1     | String                     | java.lang.String               | 255                               | 255                                  | 0                                | varchar(255)                       | false                        | true                       |
| 46 | test_filed_name2       | Test_filed_name2                  | test_filed_name2                  | 字段描述：test_filed_name2     | String                     | java.lang.String               | 255                               | 255                                  | 0                                | varchar(255)                       | false                        | true                       |
| 47 | testFiled_name3        | TestFiled_name3                   | testFiled_name3                   | 字段描述：test-filed_name3     | String                     | java.lang.String               | 255                               | 255                                  | 0                                | varchar(255)                       | false                        | true                       |
