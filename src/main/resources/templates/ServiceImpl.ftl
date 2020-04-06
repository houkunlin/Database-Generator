<@gen type="serviceImpl" />
<#assign entityClass = "${table.entityName}${settings.entitySuffix}" />
<#assign daoClass = "${table.entityName}${settings.daoSuffix}" />
<#assign serviceClass = "${table.entityName}${settings.serviceSuffix}" />
package ${settings.servicePackage}.impl;

import ${settings.entityPackage}.${entityClass};
import ${settings.daoPackage}.${daoClass};
import ${settings.servicePackage}.${serviceClass};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
* Service：${table.comment}
*
* @author ${developer.author}
* @date ${.now?string["yyyy-MM-dd HH:mm:ss"]}
*/
@CacheConfig(cacheNames = {${serviceClass}Impl.CACHE_NAME})
@Transactional(rollbackFor = Throwable.class)
@Service
public class ${serviceClass}Impl extends ServiceImpl<${daoClass}, ${entityClass}> implements ${serviceClass} {

    @Override
    public void save${table.entityName}(${entityClass} entity) {
        if (!save(entity)) {
            throw new RuntimeException("保存信息失败");
        }
    }

    @Override
    public void update${table.entityName}(${entityClass} entity) {
        boolean update = lambdaUpdate()
                // .set(${entityClass}::getId, entity.getId())
                .eq(${entityClass}::getId, entity.getId())
                .update();
        if (!update) {
            throw new RuntimeException("修改信息失败");
        }
    }

    @Override
    public void delete${table.entityName}(String primaryKey) {
        removeById(primaryKey);
    }

    @Override
    public void delete${table.entityName}(List<String> primaryKeys) {
        removeByIds(primaryKeys);
    }
}