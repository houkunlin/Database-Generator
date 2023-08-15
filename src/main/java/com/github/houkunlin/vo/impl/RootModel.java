package com.github.houkunlin.vo.impl;

import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.vo.IEntityField;
import com.github.houkunlin.vo.ITable;
import com.github.houkunlin.vo.ITableColumn;
import com.intellij.database.psi.DbTable;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final PrimaryInfo primary;

    public RootModel(DbTable dbTable, List<EntityFieldImpl> fields, List<TableColumnImpl> columns, Options options) {
        this.table = new TableImpl(dbTable);
        this.entity = new EntityImpl(dbTable, options);
        this.fields = fields;
        this.columns = columns;
        this.primary = new PrimaryInfo(fields);
    }

    public EntityImpl getEntity(Settings settings) {
        Set<String> fullTypeNames = fields.stream().map(IEntityField::getFullTypeName).collect(Collectors.toSet());
        entity.initMore(fullTypeNames, settings);
        return entity;
    }
}
