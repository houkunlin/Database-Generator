package com.github.houkunlin.vo.impl;

import com.github.houkunlin.model.TableColumnType;
import com.github.houkunlin.util.ContextUtils;
import com.github.houkunlin.vo.IEntityField;
import com.github.houkunlin.vo.IName;
import com.google.common.base.CaseFormat;
import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DataType;
import com.intellij.database.util.DasUtil;
import com.intellij.util.ReflectionUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * 实体类字段信息
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 0:59
 */
@Getter
public class EntityFieldImpl implements IEntityField {
    private final IName name;
    private final String typeName;
    private final String fullTypeName;
    private final boolean primaryKey;
    @Setter
    private String comment;
    @Setter
    private boolean selected;

    public EntityFieldImpl(DasColumn dbColumn) {
        this.name = new IName() {
            private final String value = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, dbColumn.getName());

            @Override
            public String firstUpper() {
                return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, value);
            }

            @Override
            public String firstLower() {
                return value;
            }

            @Override
            public String toString() {
                return value;
            }
        };
        String typeName = ReflectionUtil.getField(DataType.class, dbColumn.getDataType(), String.class, "typeName");
        if (typeName.contains("unsigned")) {
            typeName = typeName.replace("unsigned", "").trim();
        }
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
