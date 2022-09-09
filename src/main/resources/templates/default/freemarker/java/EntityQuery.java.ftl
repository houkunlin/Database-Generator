${gen.setFilename("${entity.name}Query.java")}
${gen.setFilepath("${settings.javaPath}/${entity.packages.entity}/")}
package ${entity.packages.entity};

${entity.packages}

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 查询对象：${entity.comment}<#if table.comment?trim?length gt 0 && entity.comment != table.comment> (${table.comment})</#if>
*
* @author ${developer.author}
*/
@ApiModel("查询对象：${entity.comment}")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${entity.name}Query {
<#list fields as field>
    <#if field.selected>
        <#if field.name?starts_with("created") || field.name?starts_with("updated") || field.name?starts_with("deleted") >
        <#else>
            /**
            * ${field.comment}
            <#if field.column.comment?trim?length gt 0 && field.comment != field.column.comment> * <p>数据库字段说明：${field.column.comment}</p></#if>
            */
            @ApiModelProperty("${field.comment}")
            private ${field.typeName} ${field.name};
        </#if>
    </#if>
</#list>
}
