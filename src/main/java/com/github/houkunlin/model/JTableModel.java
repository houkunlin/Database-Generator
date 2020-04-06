package com.github.houkunlin.model;

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

/**
 * 表格列名信息
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 9:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JTableModel extends AbstractTableModel {
    private final JTable columnTable;
    private final DbTable dbTable;
    private final com.github.houkunlin.model.Table tableInfo;
    Table<Integer, Integer, Object> table = HashBasedTable.create();
    String[] names = {"选中", "列名", "类型", "完整类型", "注释"};
    Boolean[] editable = {true, false, false, false, true};

    public JTableModel(JTable columnTable, DbTable dbTable) {
        this.columnTable = columnTable;
        this.dbTable = dbTable;
        this.tableInfo = new com.github.houkunlin.model.Table(dbTable);
        initTableContent();
        columnTable.setModel(this);
        setColumnSelected();
    }

    private void setColumnSelected() {
        TableColumn column = columnTable.getColumnModel().getColumn(0);
        column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        int width = 30;
        column.setMaxWidth(width);
        column.setMinWidth(width);
    }

    private void initTableContent() {
        // ((DbTableImpl) psiElements[0]).getDelegate().getDasChildren(ObjectKind.COLUMN).get(3)
        // ((DbTableImpl)((DbColumnImpl) psiElement).getTable()).getDelegate().getDasChildren(ObjectKind.COLUMN).get(5).getName()
        // ((MysqlImplModel.Table) delegate).getKeys().myElements.get(0).getColNames()
        // ((MysqlImplModel.Table) delegate).getPrimaryKey().getColNames()
        int rowIndex = -1;
        JBIterable<? extends DasColumn> columns = DasUtil.getColumns(dbTable);
        for (DasColumn column : columns) {
            com.github.houkunlin.model.TableColumn tableColumn = tableInfo.addColumn(column);
            int colIndex = -1;
            ++rowIndex;
            // 第1列选中复选框
            table.put(rowIndex, ++colIndex, true);
            table.put(rowIndex, ++colIndex, tableColumn.getName());
            table.put(rowIndex, ++colIndex, tableColumn.getType());
            table.put(rowIndex, ++colIndex, column.getDataType().getSpecification());
            table.put(rowIndex, ++colIndex, tableColumn.getComment());
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
            tableInfo.getColumns().get(rowIndex).setSelected((Boolean) aValue);
            update(aValue, rowIndex, columnIndex);
        } else if (columnIndex == 4) {
            tableInfo.getColumns().get(rowIndex).setComment(String.valueOf(aValue));
            update(aValue, rowIndex, columnIndex);
        }
    }

    private void update(Object aValue, int rowIndex, int columnIndex) {
        table.put(rowIndex, columnIndex, aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
