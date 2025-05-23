package com.github.houkunlin.ui.win;

import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.ui.win.table.FileTypeTableDecorator;
import com.google.common.collect.Maps;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 基础设置窗口
 *
 * @author HouKunLin
 */
public class BaseSetting implements IWindows {
    private final Project project;
    /**
     * 配置对象：设置信息
     */
    private final Settings settings;
    /**
     * 配置对象：是否覆盖文件
     */
    private final Options options;
    /**
     * 配置对象：开发者信息
     */
    private final Developer developer;
    /**
     * 输入框：开发者姓名
     */
    private JTextField authorField;
    /**
     * 输入框：电子邮件
     */
    private JTextField emailField;
    /**
     * 面板：顶级页面面板
     */
    private JPanel content;
    /**
     * 下拉框：数据库字段风格类型
     */
    private JComboBox<String> databaseFieldStyleType;

    /**
     * 复选：是否记住上次的模板
     */
    private JCheckBox retainLastSelectionTemplates;
    private JPanel fileTypeTablePanel;
    private final Runnable noteReset;

    public BaseSetting(Project project, Settings settings, Developer developer, Options options, Runnable noteReset) {
        this.project = project;
        this.settings = settings;
        this.developer = developer;
        this.options = options;
        this.noteReset = noteReset;
        initFileTypeTable();
        initDatabaseFieldStyle();
        initConfig();

        /* 普通输入框的输入事件监听 */
        class TextFieldDocumentListener implements DocumentListener {
            /**
             * 存放 setValue 与 输入框 的关系
             */
            private final Map<Consumer<String>, JTextComponent> map = Maps.newHashMap();

            public TextFieldDocumentListener() {
                map.put(developer::setAuthor, authorField);
                map.put(developer::setEmail, emailField);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentChanged(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentChanged(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            /**
             * swing 输入框组件内容更改事件
             *
             * @param e 事件
             */
            public void documentChanged(DocumentEvent e) {
                javax.swing.text.Document document = e.getDocument();
                Set<Map.Entry<Consumer<String>, JTextComponent>> entries = map.entrySet();
                for (Map.Entry<Consumer<String>, JTextComponent> entry : entries) {
                    if (TextFieldDocumentUtil.updateSettingValue(document, entry.getValue(), entry.getKey())) {
                        break;
                    }
                }
            }
        }

        TextFieldDocumentListener textFieldDocumentListener = new TextFieldDocumentListener();
        authorField.getDocument().addDocumentListener(textFieldDocumentListener);
        emailField.getDocument().addDocumentListener(textFieldDocumentListener);


    }

    private void initFileTypeTable() {
        fileTypeTablePanel.add(FileTypeTableDecorator.create(project, settings), BorderLayout.CENTER);
    }

    private void initDatabaseFieldStyle() {
        databaseFieldStyleType.addItem("下划线（LOWER_UNDERSCORE）和连接符（LOWER_HYPHEN）");
        databaseFieldStyleType.addItem("下划线（UPPER_UNDERSCORE）和连接符（LOWER_HYPHEN）");
        databaseFieldStyleType.addItem("小驼峰（LOWER_CAMEL）");
        databaseFieldStyleType.addItem("大坨峰（UPPER_CAMEL）");
        // databaseFieldStyleType.addItem("连接符（LOWER_HYPHEN）");
        databaseFieldStyleType.addItemListener(e -> {
            if (e.getStateChange() != ItemEvent.SELECTED) {
                return;
            }
            options.setDbFieldStyleType(databaseFieldStyleType.getSelectedIndex());
            this.noteReset.run();
        });
    }

    /**
     * 初始化开发者信息的输入框内容
     */
    public void initConfig() {
        authorField.setText(developer.getAuthor());
        emailField.setText(developer.getEmail());
        databaseFieldStyleType.setSelectedIndex(options.getDbFieldStyleType());
        retainLastSelectionTemplates.setSelected(options.isRetainLastSelectionTemplates());
    }

    @Override
    public JPanel getContent() {
        return content;
    }
}
