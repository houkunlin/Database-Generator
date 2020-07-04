${gen.setType("dao")}
<#assign entityClass = "${entity.name}${settings.entitySuffix}" />
<#assign daoClass = "${entity.name}${settings.daoSuffix}" />
package ${settings.daoPackage};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import ${settings.entityPackage}.${entityClass};

/**
* 存储库：${table.comment}
*
* @author ${developer.author}
* @date ${.now?string["yyyy-MM-dd HH:mm:ss"]}
*/
@Repository
public interface ${daoClass} extends BaseMapper<${entityClass}> {
}