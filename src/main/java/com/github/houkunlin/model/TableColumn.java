package com.github.houkunlin.model;

import com.google.common.base.CaseFormat;
import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DataType;
import com.intellij.database.util.DasUtil;
import com.intellij.util.ReflectionUtil;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

/**
 * 数据库表列名
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 11:44
 */
@Data
public class TableColumn {
    /**
     * 与之关联的自定义数据库表对象
     */
    @ToString.Exclude
    private final Table table;
    /**
     * 原始的列对象
     */
    private final DasColumn dbColumn;
    /**
     * 是否选中（判定是否需要生成）
     */
    private boolean selected;
    /**
     * 是否是主键
     */
    private boolean primaryKey;
    /**
     * 数据库字段（列）名称
     */
    private String name;
    /**
     * 实体字段名称
     */
    private String fieldName;
    /**
     * 实体字段名称get方法
     */
    private String fieldMethod;
    /**
     * 数据库字段类型
     */
    private String type;
    /**
     * 数据库字段（列）注释
     */
    private String comment;
    /**
     * 字段对象类型
     */
    private TableColumnType columnType;

    public TableColumn(Table table, DasColumn dbColumn) {
        this.table = table;
        this.dbColumn = dbColumn;
        DataType dataType = dbColumn.getDataType();

        name = dbColumn.getName();
        fieldName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
        fieldMethod = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
        type = ReflectionUtil.getField(DataType.class, dataType, String.class, "typeName");
        comment = StringUtils.defaultString(dbColumn.getComment(), "");

        selected = true;
        primaryKey = DasUtil.isPrimary(dbColumn);
    }

    /**
     * 获取数据库的字段类型信息
     *
     * @return 字段类型
     */
    public TableColumnType type() {
        if (type == null) {
            return table.getDefaultColumnType();
        }
        TableColumnType columnType = table.type(this.type);
        if (columnType.isDefault() && this.type.contains("unsigned")) {
            // 遇到一个无符号类型的数字，可能影响了类型的获取，因此要把无符号信息去掉，然后重新获取信息
            String unsigned = this.type.replace("unsigned", "").trim();
            columnType = table.type(unsigned);
        }

        return columnType;
    }
}
