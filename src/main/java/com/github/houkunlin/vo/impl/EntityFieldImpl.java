package com.github.houkunlin.vo.impl;

import com.github.houkunlin.model.TableColumnType;
import com.github.houkunlin.util.ContextUtils;
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
 * 实体类字段信息
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 0:59
 */
@Getter
public class EntityFieldImpl implements IEntityField {
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ITableColumn column;

    private final FieldNameInfo name;
    private final String typeName;
    private final String fullTypeName;
    private final boolean primaryKey;
    @Setter
    private String comment;
    @Setter
    private boolean selected;

    public EntityFieldImpl(DasColumn dbColumn) {
        this.name = new FieldNameInfo(dbColumn);
        String typeName = ReflectionUtil.getField(DataType.class, dbColumn.getDataType(), String.class, "typeName");
        TableColumnType columnType = type(typeName);
        this.typeName = columnType.getShortName();
        this.fullTypeName = columnType.getLongName();
        this.comment = StringUtils.defaultString(dbColumn.getComment(), "");
        this.primaryKey = DasUtil.isPrimary(dbColumn);
        this.selected = true;
    }

    public TableColumnType type(String dbType) {
        TableColumnType[] columnTypes = ContextUtils.getColumnTypes();
        if (dbType == null) {
            return TableColumnType.DEFAULT;
        }
        if (columnTypes == null) {
            return TableColumnType.DEFAULT;
        }
        for (TableColumnType columnType : columnTypes) {
            if (columnType.at(dbType)) {
                return columnType;
            }
        }
        for (TableColumnType columnType : columnTypes) {
            if (columnType.isDefault()) {
                return columnType;
            }
        }
        return columnTypes.length > 0 ? columnTypes[0] : TableColumnType.DEFAULT;
    }
}
