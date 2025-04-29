package com.github.houkunlin.ui.win.table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.table.TableCellEditor;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 通用的列信息
 *
 * @author daiwenzh5
 * @since 2.8.4
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ColumnSpec<T, V> {
    private final String name;
    private final Class<V> type;
    private final Function<T, V> getter;
    private final BiConsumer<T, V> setter;
    private boolean editable;
    private TableCellEditor cellEditor;
    private String placeholder;
    private int width;


    public static <T, V> ColumnSpec<T, V> of(String name, Class<V> type, Function<T, V> getter, BiConsumer<T, V> setter) {
        return new ColumnSpec<>(name, type, getter, setter, true, null, null, 0);
    }

    public ColumnSpec<T, V> withEditable(boolean editable) {
        this.editable = editable;
        return this;
    }

    public ColumnSpec<T, V> withCellEditor(TableCellEditor cellEditor) {
        this.cellEditor = cellEditor;
        return this;
    }

    public ColumnSpec<T, V> withPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public ColumnSpec<T, V> withWidth(int width) {
        this.width = width;
        return this;
    }

}
