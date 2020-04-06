package com.github.houkunlin.ui;

import com.github.houkunlin.model.*;
import com.github.houkunlin.util.ContextUtils;
import com.github.houkunlin.util.Generator;
import com.github.houkunlin.util.ReadJsonConfig;
import com.intellij.database.psi.DbTable;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 操作入口
 *
 * @author HouKunLin
 * @date 2020/4/2 0002 20:15
 */
public class ActionUIRunnable implements Runnable {
    /**
     * UI界面
     */
    private final ActionUI ui;
    /**
     * 当前项目信息
     */
    private Project project;
    /**
     * 操作对象元素
     */
    private PsiElement[] psiElements;
    /**
     * 配置信息
     */
    private Settings settings = ReadJsonConfig.getSettings();
    /**
     * 构建信息
     */
    private Options options = ReadJsonConfig.getOptions();
    /**
     * 开发者信息
     */
    private Developer developer = ReadJsonConfig.getDeveloper();
    /**
     * 数据库字段类型映射关系
     */
    private TableColumnType[] tableColumnTypes = ReadJsonConfig.getTableColumnTypes();

    private List<TableInfoTabUI> tableList = new ArrayList<>();

    public ActionUIRunnable(AnActionEvent actionEvent) {
        this.ui = new ActionUI();
        this.project = ContextUtils.getProject();
        this.psiElements = actionEvent.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        assert psiElements != null;
    }

    @Override
    public void run() {
        initSettings();
        initOptions();
        initDeveloper();
        configProject();
        configSelectPackage();
        for (PsiElement psiElement : psiElements) {
            if (psiElement instanceof DbTable) {
                DbTable dbTable = (DbTable) psiElement;
                TableInfoTabUI tabUI = new TableInfoTabUI(dbTable, tableColumnTypes);
                ui.getTabbedPane().addTab(dbTable.getName(), tabUI);
                tableList.add(tabUI);
            }
        }
        // 点击完成按钮
        ui.getFinishButton().addActionListener(event -> {
            //用后台任务执行代码生成
            ApplicationManager.getApplication().invokeLater(() -> {
                saveSettings();
                saveOptions();
                saveDeveloper();
                try {
                    Generator generator = new Generator(settings, options, developer);
                    for (TableInfoTabUI tabUI : tableList) {
                        tabUI.saveTableInfo();
                        Table tableInfo = tabUI.getTableInfo();
                        generator.generator(tableInfo);
                    }
                    this.finishAction();
                } catch (Exception e) {
                    e.printStackTrace();
                    Messages.showMessageDialog(e.getMessage(), "生成代码失败", Messages.getErrorIcon());
                }
            });
        });
    }

    private void initDeveloper() {
        ui.getAuthorField().setText(developer.getAuthor());
        ui.getEmailField().setText(developer.getEmail());
    }

    private void saveDeveloper() {
        developer.setAuthor(ui.getAuthorField().getText());
        developer.setEmail(ui.getEmailField().getText());
    }

    private void initSettings() {
        ui.getProjectPathField().setText(settings.getProjectPath());
        ui.getJavaPathField().setText(settings.getJavaPath());
        ui.getSourcesPathField().setText(settings.getSourcesPath());

        ui.getEntitySuffixField().setText(settings.getEntitySuffix());
        ui.getDaoSuffixField().setText(settings.getDaoSuffix());
        ui.getServiceSuffixField().setText(settings.getServiceSuffix());
        ui.getControllerSuffixField().setText(settings.getControllerSuffix());

        ui.getEntityPackageField().setText(settings.getEntityPackage());
        ui.getDaoPackageField().setText(settings.getDaoPackage());
        ui.getServicePackageField().setText(settings.getServicePackage());
        ui.getControllerPackageField().setText(settings.getControllerPackage());
        ui.getXmlPackageField().setText(settings.getXmlPackage());
    }

    private void saveSettings() {
        settings.setProjectPath(ui.getProjectPathField().getText());
        settings.setJavaPath(ui.getJavaPathField().getText());
        settings.setSourcesPath(ui.getSourcesPathField().getText());

        settings.setEntitySuffix(ui.getEntitySuffixField().getText());
        settings.setDaoSuffix(ui.getDaoSuffixField().getText());
        settings.setServiceSuffix(ui.getServiceSuffixField().getText());
        settings.setControllerSuffix(ui.getControllerSuffixField().getText());

        settings.setEntityPackage(ui.getEntityPackageField().getText());
        settings.setDaoPackage(ui.getDaoPackageField().getText());
        settings.setServicePackage(ui.getServicePackageField().getText());
        settings.setControllerPackage(ui.getControllerPackageField().getText());
        settings.setXmlPackage(ui.getXmlPackageField().getText());
        System.out.println(settings);
    }

    private void initOptions() {
        ui.getOverrideJavaCheckBox().setSelected(options.isOverrideJava());
        ui.getOverrideXmlCheckBox().setSelected(options.isOverrideXml());
        ui.getOverrideOtherCheckBox().setSelected(options.isOverrideOther());
    }

    private void saveOptions() {
        options.setOverrideJava(ui.getOverrideJavaCheckBox().isSelected());
        options.setOverrideXml(ui.getOverrideXmlCheckBox().isSelected());
        options.setOverrideOther(ui.getOverrideOtherCheckBox().isSelected());
    }

    /**
     * 完成操作
     */
    public void finishAction() {
        System.out.println("生成代码完成。");
        ProgressManager.getInstance().run(new Task.Backgroundable(project, "刷新项目...") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                Consumer<VirtualFile> refresh = (virtualFile) -> {
                    if (virtualFile != null) {
                        virtualFile.refresh(false, true);
                        System.out.println("刷新路径：" + virtualFile);
                    }
                };
                refresh.accept(LocalFileSystem.getInstance().findFileByPath(Objects.requireNonNull(project.getBasePath())));
                refresh.accept(project.getProjectFile());
                refresh.accept(project.getWorkspaceFile());
            }
        });
        ui.dispose();
        Messages.showMessageDialog("构建代码完毕", "完成", Messages.getInformationIcon());
    }

    /**
     * 配置选择包名的按钮事件
     */
    private void configSelectPackage() {
        ui.getSelectEntityPackageButton().addActionListener(e -> {
            JTextField textField = ui.getEntityPackageField();
            chooserPackage(textField.getText(), textField::setText);
        });
        ui.getSelectDaoPackageButton().addActionListener(e -> {
            JTextField textField = ui.getDaoPackageField();
            chooserPackage(textField.getText(), textField::setText);
        });
        ui.getSelectServicePackageButton().addActionListener(e -> {
            JTextField textField = ui.getServicePackageField();
            chooserPackage(textField.getText(), textField::setText);
        });
        ui.getSelectControllerPackageButton().addActionListener(e -> {
            JTextField textField = ui.getControllerPackageField();
            chooserPackage(textField.getText(), textField::setText);
        });
    }

    /**
     * 选择包名
     *
     * @param defaultSelect 默认选中包名
     * @param consumer      完成事件
     */
    private void chooserPackage(String defaultSelect, Consumer<String> consumer) {
        PackageChooserDialog chooser = new PackageChooserDialog("请选择模块包", project);
        chooser.selectPackage(defaultSelect);
        chooser.show();
        PsiPackage psiPackage = chooser.getSelectedPackage();
        if (psiPackage != null) {
            consumer.accept(psiPackage.getQualifiedName());
        }
        ui.showWindows();
        chooser.getDisposable().dispose();
    }

    /**
     * 选择项目路径
     */
    private void configProject() {
        ui.getProjectPathField().setText(project.getBasePath());
        ui.getSelectProjectPathButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooserDescriptor chooserDescriptor = FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor();
                chooserDescriptor.setTitle("选择项目路径");

                FileChooser.chooseFile(chooserDescriptor, project, null, getInitialFile(), (chosenFile) -> {
                    ui.getProjectPathField().setText(chosenFile.getPresentableUrl());
                    System.out.println(chosenFile.getPresentableUrl());
                });
                ui.showWindows();
            }
        });

    }

    /**
     * 获取默认路径地址
     *
     * @return initial file
     */
    protected VirtualFile getInitialFile() {
        String directoryName = ui.getProjectPathField().getText().trim();
        if (StringUtil.isEmptyOrSpaces(directoryName)) {
            return null;
        }

        directoryName = FileUtil.toSystemIndependentName(directoryName);
        VirtualFile path = LocalFileSystem.getInstance().findFileByPath(directoryName);
        while (path == null && directoryName.length() > 0) {
            int pos = directoryName.lastIndexOf('/');
            if (pos <= 0) {
                break;
            }
            directoryName = directoryName.substring(0, pos);
            path = LocalFileSystem.getInstance().findFileByPath(directoryName);
        }
        return path;
    }
}
