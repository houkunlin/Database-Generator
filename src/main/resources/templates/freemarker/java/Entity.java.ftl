${gen.setType("entity")}
package ${entity.packages.entity};

import com.baomidou.mybatisplus.annotation.*;

${entity.packages}

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 实体类：${entity.comment}<#if table.comment?trim?length gt 0 && entity.comment != table.comment> (${table.comment})</#if>
*
* @author ${developer.author}
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("${table.name}")
public class ${entity.name.entity} implements Serializable {
<#list fields as field>
    <#if field.selected>
        /**
        * ${field.comment}
        <#if field.column.comment?trim?length gt 0 && field.comment != field.column.comment> * <p>数据库字段说明：${field.column.comment}</p></#if>
        */
        <#if field.primaryKey>
            @TableId(type = IdType.ASSIGN_ID)
        </#if>
        <#if field.name?starts_with("created") || field.name?starts_with("updated") || field.name?starts_with("deleted")>
            @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
        </#if>
        private ${field.typeName} ${field.name};
    </#if>
</#list>
}
