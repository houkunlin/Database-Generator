${gen.setFilename("${entity.name}Vo.java")}
${gen.setFilepath("${settings.javaPath}/${entity.packages.entity}/")}
package ${entity.packages.entity};

${entity.packages}

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
* 视图对象：${entity.comment}<#if table.comment?trim?length gt 0 && entity.comment != table.comment> (${table.comment})</#if>
*
* @author ${developer.author}
*/
@ApiModel("视图对象：${entity.comment}")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${entity.name}Vo implements Serializable {
<#list fields as field>
    <#if field.selected>
        /**
        * ${field.comment}
        <#if field.column.comment?trim?length gt 0 && field.comment != field.column.comment> * <p>数据库字段说明：${field.column.comment}</p></#if>
        */
        @ApiModelProperty("${field.comment}")
        private ${field.typeName} ${field.name};
    </#if>
</#list>
}
