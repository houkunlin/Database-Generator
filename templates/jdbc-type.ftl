<#-- MyBatis 官方的JdbcType列表：https://mybatis.org/mybatis-3/apidocs/reference/org/apache/ibatis/type/JdbcType.html -->
<#assign JdbcTypes = {
"ARRAY":"ARRAY",
"BIGINT":"BIGINT",
"BINARY":"BINARY",
"BIT":"BIT",
"BLOB":"BLOB",
"BOOLEAN":"BOOLEAN",
"CHAR":"CHAR",
"CLOB":"CLOB",
"CURSOR":"CURSOR",
"DATALINK":"DATALINK",
"DATE":"DATE",
"DATETIMEOFFSET":"DATETIMEOFFSET",
"DECIMAL":"DECIMAL",
"DISTINCT":"DISTINCT",
"DOUBLE":"DOUBLE",
"FLOAT":"FLOAT",
"INTEGER":"INTEGER",
"JAVA_OBJECT":"JAVA_OBJECT",
"LONGNVARCHAR":"LONGNVARCHAR",
"LONGVARBINARY":"LONGVARBINARY",
"LONGVARCHAR":"LONGVARCHAR",
"NCHAR":"NCHAR",
"NCLOB":"NCLOB",
"NULL":"NULL",
"NUMERIC":"NUMERIC",
"NVARCHAR":"NVARCHAR",
"REAL":"REAL",
"REF":"REF",
"ROWID":"ROWID",
"SMALLINT":"SMALLINT",
"SQLXML":"SQLXML",
"STRUCT":"STRUCT",
"TIME":"TIME",
"TIMESTAMP":"TIMESTAMP",
"TINYINT":"TINYINT",
"UNDEFINED":"UNDEFINED",
"VARBINARY":"VARBINARY",
"VARCHAR":"VARCHAR"
} />
<#-- 部分未考虑到的类型映射 -->
<#assign OtherTypes = {
"DATETIME":"TIMESTAMP",
"INT":"INTEGER",
"TINYINT":"INTEGER",
"MEDIUMINT":"INTEGER",
"SMALLINT":"INTEGER",
"YEAR":"INTEGER",
"MULTIPOINT":"INTEGER",
"POINT":"INTEGER",
"TEXT":"LONGVARCHAR",
"LONGTEXT":"LONGVARCHAR",
"MEDIUMTEXT":"VARCHAR",
"MULTILINESTRING":"VARCHAR",
"TINYTEXT":"VARCHAR",
"LINESTRING":"VARCHAR",
"LONGBLOB":"BLOB",
"MEDIUMBLOB":"BLOB",
"TINYBLOB":"BLOB"
} />
<#-- 获取JdbcType信息 -->
<#function jdbcType column>
    <#assign type = column.typeName?replace(" unsigned", "")?upper_case />
    <#if JdbcTypes[type]??>
        <#return JdbcTypes[type]>
    </#if>
    <#if OtherTypes[type]??>
        <#return OtherTypes[type]>
    </#if>
    <#return 'OTHER'>
</#function>