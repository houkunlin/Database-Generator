${gen.setFilename("${entity.name}Form.java")}
${gen.setFilepath("${settings.javaPath}/${entity.packages.entity}/")}
package ${entity.packages.entity};

import com.baomidou.mybatisplus.annotation.*;

${entity.packages}

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springline.web.mvc.SpringlineCommand;
import javax.validation.constraints.NotBlank;

/**
* 表单对象：${entity.comment}<#if table.comment?trim?length gt 0 && entity.comment != table.comment> (${table.comment})</#if>
*
* @author ${developer.author}
*/
@ApiModel("表单对象：${entity.comment}")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${entity.name}Form implements Serializable {
<#list fields as field>
    <#if field.selected>
        <#if field.name?starts_with("created") || field.name?starts_with("updated") || field.name?starts_with("deleted") >
        <#else>
            /**
            * ${field.comment}
            <#if field.column.comment?trim?length gt 0 && field.comment != field.column.comment> * <p>数据库字段说明：${field.column.comment}</p></#if>
            */
            <#if field.typeName == "String">
                <#if field.primaryKey>
                    @Length(max = ${field.dataType.length}, message = "${field.comment} 在 ${field.dataType.length} 个字符以内")
                <#else>
                    <#if field.name == "remark">
                        @Length(max = ${field.dataType.length}, message = "${field.comment} 在 ${field.dataType.length} 个字符以内")
                    <#else>
                        @Length(max = ${field.dataType.length}, message = "${field.comment} 在 ${field.dataType.length} 个字符以内")
                        @NotBlank(message = "${field.comment} 不能为空")
                    </#if>
                </#if>
            </#if>
            @ApiModelProperty("${field.comment}")
            private ${field.typeName} ${field.name};
        </#if>
    </#if>
</#list>
}
