package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.IEntityField;
import com.github.houkunlin.vo.ITableColumn;
import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DataType;
import com.intellij.database.util.DasUtil;
import com.intellij.util.ReflectionUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

/**
 * 数据库表字段信息（数据库表列对象信息）
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 0:59
 */
@Getter
public class TableColumnImpl implements ITableColumn {
    /**
     * 数据库表的原始字段对象
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final DasColumn dbColumn;
    private final String name;
    private final String comment;
    private final String typeName;
    private final DataType dataType;
    private final String fullTypeName;
    private final boolean primaryKey;
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private IEntityField field;
    @Setter
    private boolean selected;

    private TableColumnImpl(String name, String typeName, String fullTypeName, String comment, boolean primaryKey, boolean selected) {
        this.dbColumn = null;
        this.name = name;
        this.typeName = typeName;
        this.dataType = null;
        this.fullTypeName = fullTypeName;
        this.comment = comment;
        this.primaryKey = primaryKey;
        this.selected = selected;
    }

    public TableColumnImpl(DasColumn dbColumn) {
        this.dbColumn = dbColumn;
        this.name = dbColumn.getName();
        this.dataType = dbColumn.getDataType();
        this.fullTypeName = dataType.getSpecification();
        this.typeName = ReflectionUtil.getField(DataType.class, dataType, String.class, "typeName");
        this.comment = StringUtils.defaultString(dbColumn.getComment(), "");
        this.primaryKey = DasUtil.isPrimary(dbColumn);
        this.selected = true;
    }

    /**
     * 创建主键字段对象
     *
     * @param name         字段名称
     * @param typeName     字段类型
     * @param fullTypeName 字段完整类型
     * @param comment      字段注释
     * @return 字段对象
     */
    public static TableColumnImpl primaryColumn(String name, String typeName, String fullTypeName, String comment) {
        return new TableColumnImpl(name, typeName, fullTypeName, comment, true, true);
    }
}
