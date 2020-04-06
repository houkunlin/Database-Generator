package com.github.houkunlin.model;

import com.google.common.base.CaseFormat;
import com.intellij.database.psi.DbColumn;
import com.intellij.database.psi.DbTable;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据库表
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 11:46
 */
@Data
public class Table {
    /**
     * 原始表对象
     */
    private final DbTable dbTable;
    /**
     * 字段列表
     */
    private final List<TableColumn> columns = new ArrayList<>();
    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 实体名称
     */
    private String entityName;
    /**
     * 实体名称对应的变量名
     */
    private String entityVar;
    /**
     * 表注释内容
     */
    private String comment;

    private List<TableColumnType> columnTypes;
    private TableColumnType defaultColumnType;

    public Table(DbTable dbTable) {
        this.dbTable = dbTable;
        this.tableName = dbTable.getName();
        this.comment = StringUtils.defaultString(dbTable.getComment(), "");
        this.entityName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
        this.entityVar = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName);
    }

    /**
     * 向表格中添加一个字段对象
     *
     * @param column 字段（列）
     * @return 新增的字段对象
     */
    public TableColumn addColumn(DbColumn column) {
        TableColumn tableColumn = new TableColumn(this, column);
        columns.add(tableColumn);
        return tableColumn;
    }

    /**
     * 设置字段类型映射配置
     *
     * @param columnTypes 字段类型映射配置信息
     */
    public void setColumnTypes(TableColumnType[] columnTypes) {
        if (columnTypes == null) {
            return;
        }
        this.columnTypes = new ArrayList<>();
        for (TableColumnType columnType : columnTypes) {
            this.columnTypes.add(columnType);
            if (columnType.isDefault()) {
                this.defaultColumnType = columnType;
            }
        }
        if (defaultColumnType == null) {
            defaultColumnType = new TableColumnType(true);
        }

        // 初始化字段类型信息
        columns.forEach(item -> {
            item.setColumnType(item.type());
        });
    }

    public TableColumnType type(String dbType) {
        TableColumnType type = defaultColumnType == null ? defaultColumnType = new TableColumnType(true) : defaultColumnType;
        if (dbType == null) {
            return type;
        }
        if (columnTypes == null) {
            return type;
        }
        for (TableColumnType columnType : columnTypes) {
            if (columnType.at(dbType)) {
                return columnType;
            }
        }
        return type;
    }

    /**
     * 获取应该引入的包列表
     *
     * @return 包列表
     */
    public Set<String> getPackages() {
        Set<String> strings = new HashSet<>();
        for (TableColumn column : columns) {
            strings.add(column.getColumnType().getLongName());
        }
        return strings;
    }
}
