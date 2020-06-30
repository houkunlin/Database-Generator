package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.IEntity;
import com.github.houkunlin.vo.IEntityField;
import com.github.houkunlin.vo.ITable;
import com.github.houkunlin.vo.ITableColumn;
import lombok.Data;

import java.util.List;

/**
 * 完整的类型信息
 *
 * @author HouKunLin
 * @date 2020/6/30 0030 16:58
 */
@Data
public class RootModel {
    /**
     * 实体对象信息
     */
    private IEntity entity;
    /**
     * 实体对象字段列表
     */
    private List<IEntityField> fields;
    /**
     * 数据库表信息
     */
    private ITable table;
    /**
     * 数据库表字段列表
     */
    private List<ITableColumn> columns;
}
