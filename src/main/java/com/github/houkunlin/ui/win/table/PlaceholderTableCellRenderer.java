package com.github.houkunlin.ui.win.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 单元格占位符渲染器，允许配置多尔个列的占位符
 *
 * @author daiwenzh5
 * @since 2.8.4
 */
public class PlaceholderTableCellRenderer extends DefaultTableCellRenderer {

    private final Map<Integer, String> placeholders = new HashMap<>();

    private final String defaultPlaceholder;

    public PlaceholderTableCellRenderer() {
        this("");
    }

    public PlaceholderTableCellRenderer(String defaultPlaceholder) {
        this.defaultPlaceholder = defaultPlaceholder;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        var component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (component instanceof JLabel label) {
            resetLabelText(value, label, column);
        }
        return component;
    }

    private void resetLabelText(Object value, JLabel label, int column) {
        if (value == null || value.toString()
                                  .trim()
                                  .isEmpty()) {
            label.setText(placeholders.getOrDefault(column, defaultPlaceholder));
//            label.setForeground(UIManager.getColor("TextField.inactiveForeground"));
            label.setForeground(UIManager.getColor("Component.infoForeground"));
        } else {
            label.setText(value.toString());
            label.setForeground(UIManager.getColor("TextField.foreground"));
        }
    }

    /**
     * 设置单个列的占位符
     *
     * @param column      列序号
     * @param placeholder 占位符
     * @return 当前对象
     */
    public PlaceholderTableCellRenderer setColumn(int column, String placeholder) {
        placeholders.put(column, placeholder);
        return this;
    }

}
