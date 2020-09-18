${gen.setType("entity")}
package ${entity.packages.entity};

import com.baomidou.mybatisplus.annotation.*;

${entity.packages}

import java.io.Serializable;
import lombok.Data;

/**
* 实体类：${entity.comment}<#if table.comment?trim?length gt 0 && entity.comment != table.comment> (${table.comment})</#if>
*
* @author ${developer.author}
* @date ${date.toString("yyyy-MM-dd HH:mm:ss")}
*/
@Data
@TableName("${table.name}")
public class ${entity.name.entity} implements Serializable {
<#list fields as field>
    <#if field.selected>

        /**
        * ${field.comment}
        */
        <#if field.primaryKey>
            @TableId(type = IdType.ASSIGN_ID)
        </#if>
        private ${field.typeName} ${field.name};
    </#if>
</#list>
}