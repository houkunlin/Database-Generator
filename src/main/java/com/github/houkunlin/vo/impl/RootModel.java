package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.IEntityField;
import com.github.houkunlin.vo.ITable;
import com.github.houkunlin.vo.ITableColumn;
import com.intellij.database.psi.DbTable;
import lombok.Getter;

import java.util.List;

/**
 * 完整的类型信息
 *
 * @author HouKunLin
 * @date 2020/6/30 0030 16:58
 */
@Getter
public class RootModel {
    /**
     * 实体对象信息
     */
    private final EntityImpl entity;
    /**
     * 实体对象字段列表
     */
    private final List<? extends IEntityField> fields;
    /**
     * 数据库表信息
     */
    private final ITable table;
    /**
     * 数据库表字段列表
     */
    private final List<? extends ITableColumn> columns;

    public RootModel(DbTable dbTable, List<? extends IEntityField> fields, List<? extends ITableColumn> columns) {
        this.table = new TableImpl(dbTable);
        this.entity = new EntityImpl(dbTable);
        this.fields = fields;
        this.columns = columns;
    }

    public EntityImpl getEntity() {
        entity.setPackages(fields);
        return entity;
    }
}
