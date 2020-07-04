${gen.setType("service")}
<#assign entityClass = "${entity.name}${settings.entitySuffix}" />
<#assign serviceClass = "${entity.name}${settings.serviceSuffix}" />
package ${settings.servicePackage};

import com.baomidou.mybatisplus.extension.service.IService;

import ${settings.entityPackage}.${entityClass};
import java.util.List;

/**
* Service：${table.comment}
*
* @author ${developer.author}
* @date ${.now?string["yyyy-MM-dd HH:mm:ss"]}
*/
public interface ${serviceClass} extends IService<${entityClass}> {
    String CACHE_NAME = "${table.name}";

    /**
     * 业务处理：保存一个 <strong>${table.comment}</strong>
     *
     * @param entity ${table.comment}
     */
    void save${entity.name}(${entityClass} entity);

    /**
     * 业务处理：修改一个 <strong>${table.comment}</strong>
     *
     * @param entity ${table.comment}
     */
    void update${entity.name}(${entityClass} entity);

    /**
     * 业务处理：删除一个 <strong>${table.comment}</strong>
     *
     * @param primaryKey 主键ID
     */
    void delete${entity.name}(String primaryKey);

    /**
     * 业务处理：删除多个 <strong>${table.comment}</strong>
     *
     * @param primaryKeys 主键ID列表
     */
    void delete${entity.name}(List<String> primaryKeys);
}