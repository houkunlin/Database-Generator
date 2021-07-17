${gen.setType("serviceImpl")}
package ${entity.packages.serviceImpl};

import ${entity.packages.entity.full};
import ${entity.packages.dao.full};
import ${entity.packages.service.full};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;

/**
* Service：${entity.comment}
*
* @author ${developer.author}
*/
@CacheConfig(cacheNames = {${entity.name.serviceImpl}.CACHE_NAME})
@Transactional(rollbackFor = Exception.class)
@Service
@AllArgsConstructor
public class ${entity.name.serviceImpl} extends ServiceImpl<${entity.name.dao}, ${entity.name.entity}> implements ${entity.name.service} {
}
