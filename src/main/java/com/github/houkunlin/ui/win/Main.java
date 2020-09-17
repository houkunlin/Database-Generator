package com.github.houkunlin.ui.win;

import com.github.houkunlin.config.ConfigService;
import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.util.ContextUtils;
import com.github.houkunlin.util.FileUtils;
import com.github.houkunlin.util.Generator;
import com.github.houkunlin.vo.impl.RootModel;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.util.List;

public class Main extends JFrame {
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
    }

    /**
     * 初始化配置信息
     */
    private void initConfig() {
        initSettings();
        Project project = ContextUtils.getProject();

        FileChooserDescriptor chooserDescriptor = FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor();
        chooserDescriptor.setTitle("选择项目路径");

        projectPathField.addBrowseFolderListener(new TextBrowseFolderListener(chooserDescriptor, project));

        // 点击完成按钮
        finishButton.addActionListener(event -> {
            // 用后台任务执行代码生成
            try {
                List<File> allSelectFile = selectTemplate.getAllSelectFile();
                if (allSelectFile.isEmpty()) {
                    Messages.showWarningDialog("当前无选中代码模板文件，无法进行代码生成，请选中至少一个代码模板文件！", "警告");
                    return;
                }
                setVisible(false);
                Generator generator = new Generator(settings, options, developer);
                ProgressManager.getInstance().run(new Task.Modal(project, "请稍候 ......", false) {
                    @Override
                    public void run(@NotNull ProgressIndicator indicator) {
                        indicator.setText("正在准备数据 ......");
                        List<RootModel> rootModels = tableSetting.getRootModels();
                        int size = rootModels.size();
                        indicator.setText("正在生成代码 ......");
                        for (int i = 0; i < size; i++) {
                            RootModel rootModel = rootModels.get(i);
                            indicator.setFraction(i * 1.0 / size);
                            generator.generator(rootModel, allSelectFile);
                        }
                        indicator.setFraction(0.0);
                        ContextUtils.refreshProject();
                        indicator.setText("正在格式化代码 ......");
                        WriteCommandAction.runWriteCommandAction(project, (Computable<List<PsiFile>>) () -> {
                            FileUtils.getInstance().reformatCode(project, generator.getSaveFiles().toArray(new PsiFile[0]));
                            return null;
                        });
                        indicator.setText("格式化代码完毕！");
                    }

                    @Override
                    public void onSuccess() {
                        super.onSuccess();
                        dispose();
                        Messages.showInfoMessage(String.format("代码构建完毕，生成 %s 个文件。", generator.getSaveFiles().size()), "完成");
                    }

                    @Override
                    public void onThrowable(@NotNull Throwable error) {
                        super.onThrowable(error);
                        Messages.showErrorDialog("代码生成失败，当前插件 2.x 版本不兼容旧版的代码模板，请升级代码模板，代码模板升级指南请查看插件介绍。\n\n" + error.getMessage(), "生成代码失败");
                        setVisible(true);
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                Messages.showErrorDialog("代码生成失败，初始化代码生成处理器失败，请联系开发者。\n\n" + throwable.getMessage(), "生成代码失败");
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
            projectPath = ContextUtils.getProject().getBasePath();
        }
        projectPathField.setText(projectPath);
        javaPathField.setText(settings.getJavaPath());
        sourcesPathField.setText(settings.getSourcesPath());
    }
}
