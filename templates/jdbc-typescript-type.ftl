<#-- MyBatis 官方的JdbcType列表：https://mybatis.org/mybatis-3/apidocs/reference/org/apache/ibatis/type/JdbcType.html -->
<#assign JdbcTypeToTypeScriptTypes = {
"ARRAY":"any",
"BIGINT":"bigint",
"BINARY":"any",
"BIT":"boolean",
"BLOB":"any",
"BOOLEAN":"boolean",
"CHAR":"string",
"CLOB":"any",
"CURSOR":"any",
"DATALINK":"any",
"DATE":"Date",
"DATETIMEOFFSET":"any",
"DECIMAL":"number",
"DISTINCT":"any",
"DOUBLE":"number",
"FLOAT":"number",
"INTEGER":"number",
"JAVA_OBJECT":"any",
"LONGNVARCHAR":"string",
"LONGVARBINARY":"any",
"LONGVARCHAR":"number",
"NCHAR":"string",
"NCLOB":"any",
"NULL":"any",
"NUMERIC":"number",
"NVARCHAR":"string",
"REAL":"any",
"REF":"any",
"ROWID":"string",
"SMALLINT":"number",
"SQLXML":"any",
"STRUCT":"any",
"TIME":"Date",
"TIMESTAMP":"Date",
"TINYINT":"number",
"UNDEFINED":"any",
"VARBINARY":"any",
"VARCHAR":"string"
} />
<#-- 部分未考虑到的类型映射 -->
<#assign OtherJdbcTypeToTypeScriptTypes = {
"DATETIME":"Date",
"INT":"number",
"TINYINT":"number",
"MEDIUMINT":"number",
"SMALLINT":"number",
"YEAR":"number",
"MULTIPOINT":"number",
"POINT":"number",
"TEXT":"string",
"LONGTEXT":"string",
"MEDIUMTEXT":"string",
"MULTILINESTRING":"string",
"TINYTEXT":"string",
"LINESTRING":"string",
"LONGBLOB":"any",
"MEDIUMBLOB":"any",
"TINYBLOB":"any"
} />
<#-- 获取 JdbcType 对应的 TypeScript 类型信息 -->
<#function getTypeScriptType column>
    <#assign type = column.typeName?replace(" unsigned", "")?upper_case />
    <#if JdbcTypeToTypeScriptTypes[type]??>
        <#return JdbcTypeToTypeScriptTypes[type]>
    </#if>
    <#if OtherJdbcTypeToTypeScriptTypes[type]??>
        <#return OtherJdbcTypeToTypeScriptTypes[type]>
    </#if>
    <#return 'any'>
</#function>