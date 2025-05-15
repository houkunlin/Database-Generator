package com.github.houkunlin.ui.win.table;

import lombok.Getter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 通用的表格模型
 *
 * @author daiwenzh5
 * @since 2.8.4
 */
@SuppressWarnings({"UnusedReturnValue"})
public class GenericTableModel<T> extends AbstractTableModel {

    private final List<ColumnSpec<T, ?>> columns = new ArrayList<>();

    @Getter
    private final List<T> data;

    public GenericTableModel(List<T> data) {
        this.data = data;
    }

    /**
     * 添加行数据
     *
     * @param items 行数据
     * @return 当前表格模型
     */
    @SafeVarargs
    public final GenericTableModel<T> addRows(T... items) {
        return addRows(List.of(items));
    }

    /**
     * 添加行数据
     * @param items 行数据
     * @return 当前表格模型
     */
    public final GenericTableModel<T> addRows(List<T> items) {
        if (items == null || items.isEmpty()) {
            return this;
        }
        var startRow = data.size();
        data.addAll(items);
        var endRow = data.size() - 1;
        fireTableRowsInserted(startRow, endRow);
        return this;
    }

    /**
     * 根据行号删除数据
     *
     * @param rows 行号
     * @return 当前表格模型
     */
    public final GenericTableModel<T> removeRows(int... rows) {
        // 将rows按降序排序
        Arrays.sort(rows);
        for (var i = rows.length - 1; i >= 0; i--) {
            this.data.remove(rows[i]);
        }
        fireTableDataChanged();
        return this;
    }

    /**
     * 添加列
     *
     * @param column 列信息
     * @return 当前表格模型
     */
    public final GenericTableModel<T> addColumn(ColumnSpec<T, ?> column) {
        columns.add(column);
        return this;
    }

    /**
     * 绑定表格
     *
     * @param table 表格
     * @return 当前表格模型
     */
    public final GenericTableModel<T> bindTable(JTable table) {
        table.setModel(this);
        PlaceholderTableCellRenderer placeholderTableCellRenderer = null;
        for (int i = 0; i < columns.size(); i++) {
            var columnSpec = columns.get(i);
            var tableColumn = table.getColumnModel()
                                   .getColumn(i);
            if (columnSpec.getCellEditor() != null) {
                tableColumn.setCellEditor(columnSpec.getCellEditor());
            }
            if (columnSpec.getPlaceholder() != null && !columnSpec.getPlaceholder()
                                                                  .isBlank()) {
                if (placeholderTableCellRenderer == null) {
                    placeholderTableCellRenderer = new PlaceholderTableCellRenderer();
                }
                tableColumn.setCellRenderer(placeholderTableCellRenderer.setColumn(i, columnSpec.getPlaceholder()));
            }
            if (columnSpec.getWidth() > 0) {
                tableColumn.setPreferredWidth(columnSpec.getWidth());
            }
        }
        return this;
    }

    @Override
    public final int getRowCount() {
        return data.size();
    }

    @Override
    public final int getColumnCount() {
        return columns.size();
    }

    @Override
    public final Object getValueAt(int row, int col) {
        T item = data.get(row);
        return columns.get(col)
                      .getGetter()
                      .apply(item);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void setValueAt(Object value, int row, int col) {
        var item = data.get(row);
        //noinspection rawtypes
        final BiConsumer setter = columns.get(col)
                                         .getSetter();
        setter.accept(item, value);
    }

    @Override
    public final String getColumnName(int column) {
        return columns.get(column)
                      .getName();
    }

    @Override
    public final Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex)
                      .getType();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return columns.get(column)
                      .isEditable();
    }

    /**
     * 清空数据
     *
     * @return 当前表格模型
     */
    public GenericTableModel<T> clear() {
        return clear(true);
    }

    /**
     * 清空数据
     *
     * @param fireTableDataChanged 是否触发表格数据改变事件
     * @return 当前表格模型
     */
    public GenericTableModel<T> clear(boolean fireTableDataChanged) {
        if (!this.data.isEmpty()) {
            data.clear();
            if (fireTableDataChanged) {
                fireTableDataChanged();
            }
        }
        return this;
    }
}
