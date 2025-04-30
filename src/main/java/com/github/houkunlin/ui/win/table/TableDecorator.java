package com.github.houkunlin.ui.win.table;

import com.github.houkunlin.message.Bundles;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.JBTable;
import lombok.Getter;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 通用的表格装饰器
 *
 * @author daiwenzh5
 * @since 2.8.4
 */
@SuppressWarnings("unchecked")
public class TableDecorator<E, T extends TableDecorator<E, T>> {

    protected final JBTable table = new JBTable();

    @Getter
    private GenericTableModel<E> model;

    public final T setModel(GenericTableModel<E> model) {
        Objects.requireNonNull(model, Bundles.message("table-decorator.not-null"));
        this.model = model.bindTable(table);
        return getSelf();
    }

    /**
     * 添加工具类
     *
     * @param configuration 配置
     * @return 带工具栏的表格面板
     */
    public final JPanel applyToolbar(BiConsumer<T, ToolbarDecorator> configuration) {
        var decorator = ToolbarDecorator.createDecorator(table);
        configuration.accept(getSelf(), decorator);
        return decorator.createPanel();
    }

    /**
     * 重置表格数据
     */
    public void reset() {
        this.model.clear();
    }

    /**
     * 重置表格数据，并添加数据
     *
     * @param data 表格数据
     */
    public void reset(List<E> data) {
        this.model
            .clear()
            .addRows(data);
    }

    private T getSelf() {
        return (T) this;
    }

}
