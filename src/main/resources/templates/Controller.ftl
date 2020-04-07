<@gen type="controller" />
<#assign entityClass = "${table.entityName}${settings.entitySuffix}" />
<#assign serviceClass = "${table.entityName}${settings.serviceSuffix}" />
<#assign serviceVar = "${table.entityVar}${settings.serviceSuffix}" />
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
@RequestMapping("/${table.tableName}")
public class ${table.entityName}${settings.controllerSuffix} extends ApiController {
    private final ${serviceClass} ${serviceVar};
    /**
     * 实体类可排序字段
     */
    private final List<SFunction<${entityClass}, ?>> entityOrderFields;

    public ${table.entityName}${settings.controllerSuffix}(${serviceClass} ${serviceVar}) {
        this.${serviceVar} = ${serviceVar};
        entityOrderFields = new ArrayList<>();
<#list table.columns as col>
    <#if col.selected>
        entityOrderFields.add(${entityClass}::get${col.fieldMethod});
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
        ${serviceVar}.save${table.entityName}(entity);

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
        ${serviceVar}.update${table.entityName}(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 删除一个 <strong>${table.comment}</strong>
     *
     * @param id 主键ID
     */
    @DeleteMapping("{id}")
    public Object delete(@PathVariable String id) {
        ${serviceVar}.delete${table.entityName}(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除多个 <strong>${table.comment}</strong>
     *
     * @param ids 主键ID列表
     */
    @DeleteMapping
    public Object deleteIds(@RequestBody List<String> ids) {
        ${serviceVar}.delete${table.entityName}(ids);
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
<#list table.columns as col>
    <#if col.selected>
        <#if col.primaryKey>
            <#if col.columnType.shortName == "String">
                lambdaQuery.eq(StringUtils.isNotBlank(entity.get${col.fieldMethod}()), ${entityClass}::get${col.fieldMethod}, entity.get${col.fieldMethod}());
            <#else>
                lambdaQuery.eq(entity.get${col.fieldMethod}() != null, ${entityClass}::get${col.fieldMethod}, entity.get${col.fieldMethod}());
            </#if>
        <#else>
            <#if col.columnType.shortName == "String">
                lambdaQuery.like(StringUtils.isNotBlank(entity.get${col.fieldMethod}()), ${entityClass}::get${col.fieldMethod}, entity.get${col.fieldMethod}());
            <#else>
                lambdaQuery.like(entity.get${col.fieldMethod}() != null, ${entityClass}::get${col.fieldMethod}, entity.get${col.fieldMethod}());
            </#if>
        </#if>
    </#if>
</#list>
        return lambdaQuery;
    }

}
