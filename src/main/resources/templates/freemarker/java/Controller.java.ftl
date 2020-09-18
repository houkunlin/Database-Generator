${gen.setType("controller")}
package ${entity.packages.controller};

import ${entity.packages.entity.full};
import ${entity.packages.service.full};

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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.List;

/**
* Controller：${entity.comment}
*
* @author ${developer.author}
* @date ${date.toString("yyyy-MM-dd HH:mm:ss")}
 */
@RestController
@RequestMapping("/${table.name}")
public class ${entity.name.controller} extends ApiController {
    private final ${entity.name.service} ${entity.name.service.firstLower};
    /**
     * 实体类可排序字段
     */
    private final List<SFunction<${entity.name.entity}, ?>> entityOrderFields;

    public ${entity.name.controller}(${entity.name.service} ${entity.name.service.firstLower}) {
        this.${entity.name.service.firstLower} = ${entity.name.service.firstLower};
        entityOrderFields = new ArrayList<>();
<#list fields as field>
    <#if field.selected>
        entityOrderFields.add(${entity.name.entity}::get${field.name.firstUpper});
    </#if>
</#list>
    }

    /**
     * 获取全部的 <strong>${entity.comment}</strong> 列表
     */
    @GetMapping("all")
    public Object listAll(@PageableDefault Pageable pageable, ${entity.name.entity} entity) {
        LambdaQueryChainWrapper<${entity.name.entity}> lambdaQuery = buildLambdaQuery(entity);
        MyBatisUtils.lambdaQueryAddOrder(lambdaQuery, pageable, entityOrderFields);
        return success(lambdaQuery.list());
    }

    /**
     * 分页获取 <strong>${entity.comment}</strong> 列表
     *
     * @param pageable 分页参数信息
     */
    @GetMapping
    public Object list(@PageableDefault Pageable pageable, ${entity.name.entity} entity) {
        LambdaQueryChainWrapper<${entity.name.entity}> lambdaQuery = buildLambdaQuery(entity);
        MyBatisUtils.lambdaQueryAddOrder(lambdaQuery, pageable, entityOrderFields);
        Page<${entity.name.entity}> page = lambdaQuery.page(MyBatisUtils.toPage(pageable, false));
        return success(page);
    }

    /**
     * 获取一个 <strong>${entity.comment}</strong>
     *
     * @param id 主键ID
     */
    @GetMapping("{id}")
    public Object info(@PathVariable ${primary.field.typeName} id) {
        return success(${entity.name.service.firstLower}.getById(id));
    }

    /**
     * 添加一个 <strong>${entity.comment}</strong>
     *
     * @param entity 修改后的信息
     */
    @PostMapping
    public Object add(@RequestBody ${entity.name.entity} entity) {
        ${entity.name.service.firstLower}.save${entity.name}(entity);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.valueOf(entity.get${primary.field.name.firstUpper}()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * 修改一个 <strong>${entity.comment}</strong>
     *
     * @param id     主键ID
     * @param entity 修改后的信息
     */
    @PutMapping("{id}")
    public Object update(@PathVariable ${primary.field.typeName} id, @RequestBody ${entity.name.entity} entity) {
        entity.set${primary.field.name.firstUpper}(id);
        ${entity.name.service.firstLower}.update${entity.name}(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 删除一个 <strong>${entity.comment}</strong>
     *
     * @param id 主键ID
     */
    @DeleteMapping("{id}")
    public Object delete(@PathVariable ${primary.field.typeName} id) {
        ${entity.name.service.firstLower}.delete${entity.name}(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除多个 <strong>${entity.comment}</strong>
     *
     * @param ids 主键ID列表
     */
    @DeleteMapping
    public Object deleteIds(@RequestBody List<${primary.field.typeName}> ids) {
        ${entity.name.service.firstLower}.delete${entity.name}(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 构建查询条件内容
     *
     * @param entity 实体对象
     * @return lambda query chain wrapper
     */
    private LambdaQueryChainWrapper<${entity.name.entity}> buildLambdaQuery(${entity.name.entity} entity) {
        LambdaQueryChainWrapper<${entity.name.entity}> lambdaQuery = ${entity.name.service.firstLower}.lambdaQuery();
<#list fields as field>
    <#if field.selected>
        <#if field.primaryKey>
            <#if field.typeName == "String">
                lambdaQuery.eq(StringUtils.isNotBlank(entity.get${field.name.firstUpper}()), ${entity.name.entity}::get${field.name.firstUpper}, entity.get${field.name.firstUpper}());
            <#else>
                lambdaQuery.eq(entity.get${field.name.firstUpper}() != null, ${entity.name.entity}::get${field.name.firstUpper}, entity.get${field.name.firstUpper}());
            </#if>
        <#else>
            <#if field.typeName == "String">
                lambdaQuery.like(StringUtils.isNotBlank(entity.get${field.name.firstUpper}()), ${entity.name.entity}::get${field.name.firstUpper}, entity.get${field.name.firstUpper}());
            <#else>
                lambdaQuery.eq(entity.get${field.name.firstUpper}() != null, ${entity.name.entity}::get${field.name.firstUpper}, entity.get${field.name.firstUpper}());
            </#if>
        </#if>
    </#if>
</#list>
        return lambdaQuery;
    }

    /**
     * 构建查询条件内容
     *
     * @param entity 实体对象
     * @return lambda query chain wrapper
     */
    private QueryWrapper<${entity.name.entity}> buildQuery(${entity.name.entity} entity) {
         QueryWrapper<${entity.name.entity}> wrapper = new QueryWrapper<>();
<#list fields as field>
    <#if field.selected>
        <#if field.primaryKey>
            <#if field.typeName == "String">
                wrapper.eq(StringUtils.isNotBlank(entity.get${field.name.firstUpper}()), "${field.column.name}", entity.get${field.name.firstUpper}());
            <#else>
                wrapper.eq(entity.get${field.name.firstUpper}() != null, "${field.column.name}", entity.get${field.name.firstUpper}());
            </#if>
        <#else>
            <#if field.typeName == "String">
                wrapper.like(StringUtils.isNotBlank(entity.get${field.name.firstUpper}()), "${field.column.name}", entity.get${field.name.firstUpper}());
            <#else>
                wrapper.eq(entity.get${field.name.firstUpper}() != null, "${field.column.name}", entity.get${field.name.firstUpper}());
            </#if>
        </#if>
    </#if>
</#list>
        return wrapper;
    }

}
