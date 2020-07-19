package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.IEntityField;
import com.github.houkunlin.vo.ITableColumn;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 主键信息
 *
 * @author HouKunLin
 * @date 2020/7/18 0018 21:49
 */
@Getter
public class PrimaryInfo {
    /**
     * 主键的Java字段对象
     */
    private final IEntityField field;

    /**
     * 主键的数据库字段对象
     */
    private final ITableColumn column;

    /**
     * 实体对象字段列表（主键列表）
     */
    private final List<? extends IEntityField> fields;

    /**
     * 数据库表字段列表（主键列表）
     */
    private final List<? extends ITableColumn> columns;

    public PrimaryInfo(List<EntityFieldImpl> fields) {
        List<EntityFieldImpl> collect = fields.stream().filter(EntityFieldImpl::isPrimaryKey).collect(Collectors.toList());
        EntityFieldImpl primaryField;
        if (collect.isEmpty()) {
            // 没有主键对象，创建一个默认的主键对象
            primaryField = EntityFieldImpl.primaryField("id", "String", "java.lang.String", "主键ID");
            TableColumnImpl tableColumn = TableColumnImpl.primaryColumn("id", "varchar", "varchar(255)", "主键ID");
            primaryField.setColumn(tableColumn);
            tableColumn.setField(primaryField);
        } else {
            primaryField = collect.get(0);
        }
        this.field = primaryField;
        this.column = primaryField.getColumn();
        this.fields = collect;
        this.columns = collect.stream().map(EntityFieldImpl::getColumn).collect(Collectors.toList());
    }
}
