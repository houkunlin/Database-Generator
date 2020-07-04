${gen.setType("entity")}
<#assign entityClass = "${entity.name}${settings.entitySuffix}" />
package ${settings.entityPackage};

import com.baomidou.mybatisplus.annotation.*;

<#list entity.getPackages() as package>
    import ${package};
</#list>

import java.io.Serializable;
import lombok.Data;

/**
* 实体类：${table.comment}
*
* @author ${developer.author}
* @date ${.now?string["yyyy-MM-dd HH:mm:ss"]}
*/
@Data
@TableName("${table.name}")
public class ${entityClass} implements Serializable {
<#list fields as field>
    <#if field.selected>

        /**
        * ${field.comment}
        */
        <#if field.primaryKey>
            @TableId(type = IdType.ASSIGN_UUID)
        </#if>
        private ${field.typeName} ${field.name};
    </#if>
</#list>
}