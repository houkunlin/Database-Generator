${gen.setType("service")}
package ${entity.packages.service};

import com.baomidou.mybatisplus.extension.service.IService;

import ${entity.packages.entity.full};
import java.util.List;

/**
* Service：${table.comment}
*
* @author ${developer.author}
* @date ${.now?string["yyyy-MM-dd HH:mm:ss"]}
*/
public interface ${entity.name.service} extends IService<${entity.name.entity}> {
    String CACHE_NAME = "${table.name}";

    /**
     * 业务处理：保存一个 <strong>${table.comment}</strong>
     *
     * @param entity ${table.comment}
     */
    void save${entity.name}(${entity.name.entity} entity);

    /**
     * 业务处理：修改一个 <strong>${table.comment}</strong>
     *
     * @param entity ${table.comment}
     */
    void update${entity.name}(${entity.name.entity} entity);

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