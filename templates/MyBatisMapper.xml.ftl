<#include "jdbc-type.ftl">
${gen.setType("xml")}<#-- 生成 MyBatis 的 Mapper.xml 内容代码模板 -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${entity.packages.dao.full}">
    <resultMap id="BaseResultMap" type="${entity.packages.entity.full}">
        <#list columns as column>
        <#-- ${column.name}:${column.typeName} -->
            <#if column.primaryKey>
                <id column="${column.name}" jdbcType="${jdbcType(column)}" property="${column.field.name}"/>
            <#else>
                <result column="${column.name}" jdbcType="${jdbcType(column)}" property="${column.field.name}"/>
            </#if>
        </#list>
    </resultMap>
    <sql id="Base_Column_List">
        <#list columns as column>${column.name}<#if column?has_next>,</#if></#list>
    </sql>
    <select id="selectByPrimaryKey" parameterType="${primary.field.fullTypeName}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${table.name}
        where ${primary.column.name} = ${r'#'}{${primary.field.name},jdbcType=${jdbcType(primary.column)}}
    </select>
    <delete id="deleteByPrimaryKey" parameterType="${primary.field.fullTypeName}">
        delete from ${table.name}
        where ${primary.column.name} = ${r'#'}{${primary.field.name},jdbcType=${jdbcType(primary.column)}}
    </delete>
    <insert id="insert" keyColumn="${primary.column.name}" keyProperty="${primary.field.name}"
            parameterType="${entity.packages.entity.full}" useGeneratedKeys="true">
        insert into ${table.name} (
        <#list columns as column>${column.name}<#if column?has_next>,</#if></#list>
        )
        values (
        <#list columns as column>${r'#'}{${column.field.name},jdbcType=${jdbcType(column)}}<#if column?has_next>,</#if></#list>
        )
    </insert>
    <insert id="insertSelective" keyColumn="${primary.column.name}" keyProperty="${primary.field.name}"
            parameterType="${entity.packages.entity.full}" useGeneratedKeys="true">
        insert into ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list columns as column>
                <if test="${column.field.name} != null">
                    ${column.name},
                </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list columns as column>
                <if test="${column.field.name} != null">
                    ${r'#'}{${column.field.name},jdbcType=${jdbcType(column)}},
                </if>
            </#list>
        </trim>
    </insert>
    <update id="updateByPrimaryKeySelective" parameterType="${entity.packages.entity.full}">
        update ${table.name}
        <set>
            <#list columns as column>
                <if test="${column.field.name} != null">
                    ${column.name} = ${r'#'}{${column.field.name},jdbcType=${jdbcType(column)}},
                </if>
            </#list>
        </set>
        where ${primary.column.name} = ${r'#'}{${primary.field.name},jdbcType=${jdbcType(primary.column)}}
    </update>
    <update id="updateByPrimaryKey" parameterType="${entity.packages.entity.full}">
        update ${table.name}
        set
        <#list columns as column>
            ${column.name} = ${r'#'}{${column.field.name},jdbcType=${jdbcType(column)}}<#if column?has_next>,</#if>
        </#list>
        where ${primary.column.name} = ${r'#'}{${primary.field.name},jdbcType=${jdbcType(primary.column)}}
    </update>
</mapper>