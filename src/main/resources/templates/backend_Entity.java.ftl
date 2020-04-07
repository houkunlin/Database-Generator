<@gen type="entity" />
<#assign entityClass = "${table.entityName}${settings.entitySuffix}" />
package ${settings.entityPackage};

import com.baomidou.mybatisplus.annotation.*;

<#list table.getPackages() as package>
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
@TableName("${table.tableName}")
public class ${entityClass} implements Serializable {
<#list table.columns as col>
    <#if col.selected>

        /**
        * ${col.comment}
        */
        <#if col.primaryKey>
            @TableId(type = IdType.ASSIGN_UUID)
        </#if>
        private ${col.columnType.shortName} ${col.fieldName};
    </#if>
</#list>

}