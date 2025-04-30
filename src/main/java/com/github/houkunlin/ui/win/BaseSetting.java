package com.github.houkunlin.ui.win;

import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.ui.win.table.FileTypeTableDecorator;
import com.google.common.collect.Maps;

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
 * @date 2020/8/15 0015 16:00
 */
public class BaseSetting implements IWindows {
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

    public BaseSetting(Settings settings, Developer developer, Options options, Runnable noteReset) {
        this.settings = settings;
        this.developer = developer;
        this.options = options;
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
        fileTypeTablePanel.add(FileTypeTableDecorator.create(settings), BorderLayout.CENTER);
    }

    private void initDatabaseFieldStyle() {
        databaseFieldStyleType.addItem("下划线（LOWER_UNDERSCORE）");
        databaseFieldStyleType.addItem("下划线（UPPER_UNDERSCORE）");
        databaseFieldStyleType.addItem("小驼峰（LOWER_CAMEL）");
        databaseFieldStyleType.addItem("大坨峰（UPPER_CAMEL）");
        databaseFieldStyleType.addItem("连接符（LOWER_HYPHEN）");
        databaseFieldStyleType.addItemListener(e -> {
            if (e.getStateChange() != ItemEvent.SELECTED) {
                return;
            }
            options.setDbFieldStyleType(databaseFieldStyleType.getSelectedIndex());
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
