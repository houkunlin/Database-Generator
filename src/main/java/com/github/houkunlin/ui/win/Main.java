package com.github.houkunlin.ui.win;

import com.github.houkunlin.config.ConfigService;
import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.icon.DatabaseIcons;
import com.github.houkunlin.task.GeneratorTask;
import com.github.houkunlin.util.Generator;
import com.github.houkunlin.util.PluginUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiElement;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class Main extends JFrame {

    private static final Logger log = Logger.getInstance(Main.class);

    /**
     * 配置对象
     */
    private final ConfigService configService;
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
     * 面板对象：模板选择配置
     */
    private final SelectTemplate selectTemplate;
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
     * 输入框：输入内容监听
     */
    private final DocumentListener documentListener = new DocumentListener() {

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
            if (!TextFieldDocumentUtil.updateSettingValue(document, javaPathField, settings::setJavaPath)) {
                if (!TextFieldDocumentUtil.updateSettingValue(document, sourcesPathField, settings::setSourcesPath)) {
                    TextFieldDocumentUtil.updateSettingValue(document, projectPathField.getTextField(), settings::setProjectPath);
                }
            }
        }
    };
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
    private JButton refreshConfig;

    public Main(PsiElement[] psiElements, ConfigService configService) {
        super("代码生成器");
        this.configService = configService;
        this.settings = configService.getSettings();
        this.developer = configService.getDeveloper();
        this.options = configService.getOptions();
        baseSetting = new BaseSetting(settings, developer, options);
        selectTemplate = new SelectTemplate();
        tableSetting = new TableSetting(psiElements);
        tableTabbedPane.addTab("基础配置", baseSetting.getContent());
        tableTabbedPane.addTab("模板选择", selectTemplate.getContent());
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
        refreshConfig.setEnabled(true);
    }

    /**
     * 初始化配置信息
     */
    private void initConfig() {
        initSettings();
        Project project = PluginUtils.getProject();

        FileChooserDescriptor chooserDescriptor = FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor();
        chooserDescriptor.setTitle("选择项目路径");

        projectPathField.addBrowseFolderListener(new TextBrowseFolderListener(chooserDescriptor, project));

        refreshConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton jButton = (JButton) e.getSource();
                Project project = PluginUtils.getProject();

                jButton.setEnabled(false);
                ConfigService  configService = ConfigService.getInstance(project);
                if (configService != null) {
                    configService.refresh();
                    refreshMainConfig();
                    baseSetting.initConfig();
                }
                jButton.setEnabled(true);
                refreshConfig.setIcon(DatabaseIcons.REFRESH);
            }
        });
        // 点击完成按钮 默认
        finishButton.addActionListener(event -> {
            try {
                List<File> allSelectFile = selectTemplate.getAllSelectFile();
                if (allSelectFile.isEmpty()) {
                    Messages.showWarningDialog("当前无选中代码模板文件，无法进行代码生成，请选中至少一个代码模板文件！", "警告");
                    return;
                }
                setVisible(false);
                Generator generator = new Generator(settings, options, developer);
                GeneratorTask generatorTask = new GeneratorTask(project, this, generator, allSelectFile, tableSetting.getRootModels());
                ProgressManager.getInstance().run(generatorTask);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                setVisible(true);
                Messages.showErrorDialog("初始化代码生成处理器失败，请联系开发者。\n\n" + throwable.getMessage(), "生成代码失败");
            }
            configService.setDeveloper(developer);
            configService.setOptions(options);
            configService.setSettings(settings);
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
     * 初始化设置信息
     */
    private void initSettings() {
        projectPathField.getTextField().getDocument().addDocumentListener(documentListener);
        javaPathField.getDocument().addDocumentListener(documentListener);
        sourcesPathField.getDocument().addDocumentListener(documentListener);

        String projectPath = settings.getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            projectPath = PluginUtils.getProject().getBasePath();
        }
        projectPathField.setText(projectPath);
    }

    private void refreshMainConfig(){
        javaPathField.setText(settings.getJavaPath());
        sourcesPathField.setText(settings.getSourcesPath());
    }
}
