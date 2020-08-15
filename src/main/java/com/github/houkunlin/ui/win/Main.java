package com.github.houkunlin.ui.win;

import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.util.ContextUtils;
import com.github.houkunlin.util.Generator;
import com.github.houkunlin.vo.impl.RootModel;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiElement;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main extends JFrame {
    /**
     * 配置对象：设置信息
     */
    private final Settings settings;
    /**
     * 配置对象：开发者信息
     */
    private final Developer developer;
    /**
     * 配置对象：参数信息
     */
    private final Options options;
    /**
     * 面板对象：数据库表配置
     */
    private final TableSetting tableSetting;
    /**
     * 面板对象：基础信息配置
     */
    private final BaseSetting baseSetting;
    /**
     * 输入框：项目路径输入、选择
     */
    private TextFieldWithBrowseButton projectPathField;
    /**
     * 输入框：Java代码路径
     */
    private JTextField javaPathField;
    /**
     * 输入框：资源代码路径
     */
    private JTextField sourcesPathField;
    /**
     * 内容Tab标签
     */
    private JTabbedPane tableTabbedPane;
    /**
     * 按钮：完成按钮
     */
    private JButton finishButton;
    /**
     * 面板：内容面板
     */
    private JPanel content;

    public Main(PsiElement[] psiElements, Settings settings, Developer developer, Options options) {
        super("代码生成器");
        this.settings = settings;
        this.developer = developer;
        this.options = options;
        baseSetting = new BaseSetting(settings, developer, options);
        tableSetting = new TableSetting(psiElements);
        tableTabbedPane.addTab("基础配置", baseSetting.getContent());
        tableTabbedPane.addTab("数据库表配置", tableSetting.getContent());
        initWindows();
        initConfig();
    }

    /**
     * 初始化窗口配置
     */
    private void initWindows() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(content);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        int frameWidth = content.getPreferredSize().width;
        int frameHeight = content.getPreferredSize().height;
        setLocation((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2);
        setResizable(false);
        pack();
        setVisible(true);
    }

    /**
     * 初始化配置信息
     */
    private void initConfig() {
        initSettings();
        Project project = ContextUtils.getProject();
        projectPathField.setText(project.getBasePath());

        FileChooserDescriptor chooserDescriptor = FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor();
        chooserDescriptor.setTitle("选择项目路径");

        projectPathField.addBrowseFolderListener(new TextBrowseFolderListener(chooserDescriptor, project));

        // 点击完成按钮
        finishButton.addActionListener(event -> {
            //用后台任务执行代码生成
            this.save();
            ApplicationManager.getApplication().invokeLater(() -> {
                try {
                    Generator generator = new Generator(settings, options, developer);
                    List<RootModel> rootModels = tableSetting.getRootModels();
                    for (RootModel rootModel : rootModels) {
                        generator.generator(rootModel);
                    }
                    this.finishAction();
                } catch (Exception e) {
                    e.printStackTrace();
                    Messages.showMessageDialog("代码生成失败，当前插件 2.x 版本不兼容旧版的代码模板，请升级代码模板，代码模板升级指南请查看插件介绍。\n\n" + e.getMessage(), "生成代码失败", Messages.getErrorIcon());
                }
            });
        });
    }

    /**
     * 显示窗口
     */
    public void showWindows() {
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
    }

    /**
     * 完成操作
     */
    public void finishAction() {
        ContextUtils.refreshProject();
        dispose();
        Messages.showMessageDialog("构建代码完毕", "完成", Messages.getInformationIcon());
    }

    /**
     * 初始化设置信息
     */
    private void initSettings() {
        projectPathField.setText(settings.getProjectPath());
        javaPathField.setText(settings.getJavaPath());
        sourcesPathField.setText(settings.getSourcesPath());
    }

    /**
     * 保存设置信息
     */
    public void save() {
        settings.setProjectPath(projectPathField.getText());
        settings.setJavaPath(javaPathField.getText());
        settings.setSourcesPath(sourcesPathField.getText());
    }
}
