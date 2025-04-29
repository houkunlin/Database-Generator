package com.github.houkunlin.ui.win.table;

import com.intellij.openapi.ui.ComboBox;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 单元格下拉输入框
 *
 * @author daiwenzh5
 * @since 2.8.4
 */
public class ComboBoxTableCellEditor<T> extends DefaultCellEditor {

    @SafeVarargs
    public ComboBoxTableCellEditor(T... items) {
        super(createComboBox(items));
    }

    private static <T> @NotNull ComboBox<T> createComboBox(T[] items) {
        var comboBox = new ComboBox<>(items);
        comboBox.setEditable(true);
        return comboBox;
    }


}
