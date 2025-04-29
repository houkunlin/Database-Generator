package com.github.houkunlin.ui.win.table;

import com.github.houkunlin.config.Settings;
import com.github.houkunlin.model.FileType;
import com.github.houkunlin.util.PluginUtils;
import com.intellij.openapi.actionSystem.ActionToolbarPosition;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 文件类型表格
 *
 * @author daiwenzh5
 * @since 2.8.4
 */
public class FileTypeTableDecorator extends TableDecorator<FileType, FileTypeTableDecorator> {

    private FileTypeTableDecorator() {
    }

    public static JPanel create(Settings settings) {
        return new FileTypeTableDecorator()
            .setModel(createTableModel(settings))
            .applyToolbar((tableDecorator, toolbarDecorator) -> configurationToolbar(settings, tableDecorator, toolbarDecorator));
    }

    private static void configurationToolbar(Settings settings, FileTypeTableDecorator tableDecorator, ToolbarDecorator toolbarDecorator) {
        toolbarDecorator
            .setToolbarPosition(ActionToolbarPosition.TOP)
            .setAddAction(btn -> tableDecorator.getModel()
                                               // 添加新行并设置默认值
                                               .addRows(FileType.of("", "", "", ".java", settings.getJavaPath(), true)))
            .setRemoveAction(btn -> {
                if (tableDecorator.table.getSelectedRows().length == 0) {
                    return;
                }
                tableDecorator.getModel()
                              .removeRows(tableDecorator.table.getSelectedRows());
            });
    }

    private static @NotNull GenericTableModel<FileType> createTableModel(Settings settings) {
        var editorTableCellEditor = new EditorTableCellEditor();
        var extTableCellEditor = new ComboBoxTableCellEditor<>(".java", ".kt", ".xml");
        var javaPath = ObjectUtils.notNull(settings.getJavaPath(), "src/main/java");
        var pathTableCellEditor = new ComboBoxTableCellEditor<>(
            javaPath,
            javaPath.replace("java", "kotlin"),
            settings.getResourcesPath()
        );
        return new GenericTableModel<>(settings.getFileTypes())
            .addColumn(ColumnSpec.of("类型", String.class, FileType::getType, FileType::setType))
            .addColumn(ColumnSpec.of("后缀", String.class, FileType::getSuffix, FileType::setSuffix))
            .addColumn(ColumnSpec.of("包名", String.class, FileType::getPackageName, FileType::setPackageName)
                                 .withWidth(120)
                                 .withCellEditor(editorTableCellEditor))
            .addColumn(ColumnSpec.of("拓展名", String.class, FileType::getExt, FileType::setExt)
                                 .withCellEditor(extTableCellEditor)
                                 .withWidth(50))
            .addColumn(ColumnSpec.of("存储路径", String.class, FileType::getPath, FileType::setPath)
                                 .withCellEditor(pathTableCellEditor)
                                 .withPlaceholder("默认为代码路径")
                                 .withWidth(200))
            .addColumn(ColumnSpec.of("允许覆盖", Boolean.class, FileType::isOverride, FileType::setOverride)
                                 .withWidth(30));
    }

    @Override
    public void reset() {
        var fileTypes = PluginUtils.loadConfig()
                                   .getSettings()
                                   .getFileTypes();
        this.reset(fileTypes);
    }
}
