package com.github.houkunlin.model;

import com.github.houkunlin.config.Options;
import com.github.houkunlin.vo.impl.EntityFieldImpl;
import com.github.houkunlin.vo.impl.TableColumnImpl;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.intellij.database.model.DasColumn;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.util.containers.JBIterable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.List;

/**
 * 表格列名信息
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 9:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JTableModel extends AbstractTableModel {
    private final List<EntityFieldImpl> fieldImpls = new ArrayList<>();
    private final List<TableColumnImpl> columnImpls = new ArrayList<>();

    Table<Integer, Integer, Object> table = HashBasedTable.create();
    String[] names = {"选中", "DB列名", "Java字段", "DB类型", "Java类型", "注释"};
    Boolean[] editable = {true, false, false, false, false, true};

    public JTableModel(JTable columnTable, DbTable dbTable, Options options) {
        initTableContent(dbTable, options);
        columnTable.setModel(this);
        setColumnSelected(columnTable);
    }

    private void setColumnSelected(JTable columnTable) {
        TableColumn column = columnTable.getColumnModel().getColumn(0);
        column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        int width = 30;
        column.setMaxWidth(width);
        column.setMinWidth(width);
    }

    private void initTableContent(DbTable dbTable, Options options) {
        // ((DbTableImpl) psiElements[0]).getDelegate().getDasChildren(ObjectKind.COLUMN).get(3)
        // ((DbTableImpl)((DbColumnImpl) psiElement).getTable()).getDelegate().getDasChildren(ObjectKind.COLUMN).get(5).getName()
        // ((MysqlImplModel.Table) delegate).getKeys().myElements.get(0).getColNames()
        // ((MysqlImplModel.Table) delegate).getPrimaryKey().getColNames()
        int rowIndex = -1;
        JBIterable<? extends DasColumn> columns = DasUtil.getColumns(dbTable);
        for (DasColumn column : columns) {
            EntityFieldImpl entityField = new EntityFieldImpl(column, options);
            TableColumnImpl tableColumn = new TableColumnImpl(column);
            entityField.setColumn(tableColumn);
            tableColumn.setField(entityField);
            fieldImpls.add(entityField);
            columnImpls.add(tableColumn);

            int colIndex = -1;
            ++rowIndex;
            // 第0列选中复选框
            table.put(rowIndex, ++colIndex, true);
            // 第1列：DB列名
            table.put(rowIndex, ++colIndex, tableColumn.getName());
            // 第2列：Java字段
            table.put(rowIndex, ++colIndex, entityField.getName());
            // 第3列：DB类型
            table.put(rowIndex, ++colIndex, tableColumn.getFullTypeName());
            // 第4列：Java类型
            table.put(rowIndex, ++colIndex, entityField.getTypeName());
            // 第5列：注释
            table.put(rowIndex, ++colIndex, entityField.getComment());
        }
    }

    @Override
    public int getRowCount() {
        return table.rowKeySet().size();
    }

    @Override
    public int getColumnCount() {
        return table.columnKeySet().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return table.get(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return editable[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            fieldImpls.get(rowIndex).setSelected((Boolean) aValue);
            columnImpls.get(rowIndex).setSelected((Boolean) aValue);
            update(aValue, rowIndex, columnIndex);
        } else if (columnIndex == names.length - 1) {
            fieldImpls.get(rowIndex).setComment(String.valueOf(aValue));
            update(aValue, rowIndex, columnIndex);
        }
    }

    private void update(Object aValue, int rowIndex, int columnIndex) {
        table.put(rowIndex, columnIndex, aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
