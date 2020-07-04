${gen.setType("controller")}
<#assign entityClass = "${entity.name}${settings.entitySuffix}" />
<#assign serviceClass = "${entity.name}${settings.serviceSuffix}" />
<#assign serviceVar = "${entity.getNameFirstLower()}${settings.serviceSuffix}" />
package ${settings.controllerPackage};

import ${settings.entityPackage}.${entityClass};
import ${settings.servicePackage}.${serviceClass};

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.utils.MyBatisUtils;

import java.util.ArrayList;
import java.util.List;

/**
* Controller：${table.comment}
*
* @author ${developer.author}
* @date ${.now?string["yyyy-MM-dd HH:mm:ss"]}
 */
@RestController
@RequestMapping("/${table.name}")
public class ${entity.name}${settings.controllerSuffix} extends ApiController {
    private final ${serviceClass} ${serviceVar};
    /**
     * 实体类可排序字段
     */
    private final List<SFunction<${entityClass}, ?>> entityOrderFields;

    public ${entity.name}${settings.controllerSuffix}(${serviceClass} ${serviceVar}) {
        this.${serviceVar} = ${serviceVar};
        entityOrderFields = new ArrayList<>();
<#list fields as field>
    <#if field.selected>
        entityOrderFields.add(${entityClass}::get${field.getNameFirstUpper()});
    </#if>
</#list>
    }

    /**
     * 获取全部的 <strong>${table.comment}</strong> 列表
     */
    @GetMapping("all")
    public Object listAll(@PageableDefault(sort = {"gmtCreate"}) Pageable pageable, ${entityClass} entity) {
        LambdaQueryChainWrapper<${entityClass}> lambdaQuery = buildLambdaQuery(entity);
        MyBatisUtils.lambdaQueryAddOrder(lambdaQuery, pageable, entityOrderFields);
        return success(lambdaQuery.list());
    }

    /**
     * 分页获取 <strong>${table.comment}</strong> 列表
     *
     * @param pageable 分页参数信息
     */
    @GetMapping
    public Object list(@PageableDefault(sort = {"gmtCreate"}) Pageable pageable, ${entityClass} entity) {
        LambdaQueryChainWrapper<${entityClass}> lambdaQuery = buildLambdaQuery(entity);
        MyBatisUtils.lambdaQueryAddOrder(lambdaQuery, pageable, entityOrderFields);
        Page<${entityClass}> page = lambdaQuery.page(MyBatisUtils.toPage(pageable, false));
        return success(page);
    }

    /**
     * 获取一个 <strong>${table.comment}</strong>
     *
     * @param id 主键ID
     */
    @GetMapping("{id}")
    public Object info(@PathVariable String id) {
        return success(${serviceVar}.getById(id));
    }

    /**
     * 添加一个 <strong>${table.comment}</strong>
     *
     * @param entity 修改后的信息
     */
    @PostMapping
    public Object add(@RequestBody ${entityClass} entity) {
        ${serviceVar}.save${entity.name}(entity);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.valueOf(entity.get${(table.getPrimaryColumn().fieldMethod)!'Id'}()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * 修改一个 <strong>${table.comment}</strong>
     *
     * @param id     主键ID
     * @param entity 修改后的信息
     */
    @PutMapping("{id}")
    public Object update(@PathVariable String id, @RequestBody ${entityClass} entity) {
        entity.setId(id);
        ${serviceVar}.update${entity.name}(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 删除一个 <strong>${table.comment}</strong>
     *
     * @param id 主键ID
     */
    @DeleteMapping("{id}")
    public Object delete(@PathVariable String id) {
        ${serviceVar}.delete${entity.name}(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除多个 <strong>${table.comment}</strong>
     *
     * @param ids 主键ID列表
     */
    @DeleteMapping
    public Object deleteIds(@RequestBody List<String> ids) {
        ${serviceVar}.delete${entity.name}(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 构建查询条件内容
     *
     * @param entity 实体对象
     * @return lambda query chain wrapper
     */
    private LambdaQueryChainWrapper<${entityClass}> buildLambdaQuery(${entityClass} entity) {
        LambdaQueryChainWrapper<${entityClass}> lambdaQuery = ${serviceVar}.lambdaQuery();
<#list fields as field>
    <#if field.selected>
        <#if field.primaryKey>
            <#if field.typeName == "String">
                lambdaQuery.eq(StringUtils.isNotBlank(entity.get${field.getNameFirstUpper()}()), ${entityClass}::get${field.getNameFirstUpper()}, entity.get${field.getNameFirstUpper()}());
            <#else>
                lambdaQuery.eq(entity.get${field.getNameFirstUpper()}() != null, ${entityClass}::get${field.getNameFirstUpper()}, entity.get${field.getNameFirstUpper()}());
            </#if>
        <#else>
            <#if field.typeName == "String">
                lambdaQuery.like(StringUtils.isNotBlank(entity.get${field.getNameFirstUpper()}()), ${entityClass}::get${field.getNameFirstUpper()}, entity.get${field.getNameFirstUpper()}());
            <#else>
                lambdaQuery.like(entity.get${field.getNameFirstUpper()}() != null, ${entityClass}::get${field.getNameFirstUpper()}, entity.get${field.getNameFirstUpper()}());
            </#if>
        </#if>
    </#if>
</#list>
        return lambdaQuery;
    }

}
