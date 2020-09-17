package com.github.houkunlin.task;

import com.github.houkunlin.util.ContextUtils;
import com.github.houkunlin.util.FileUtils;
import com.github.houkunlin.util.Generator;
import com.github.houkunlin.vo.impl.RootModel;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * @author HouKunLin
 * @date 2020/9/18 0018 0:31
 */
public class GeneratorTask extends Task.Modal {
    private final Project project;
    private final JFrame windows;
    private final Generator generator;
    private final List<File> templates;
    private final List<RootModel> rootModels;

    public GeneratorTask(@Nullable Project project, JFrame windows, Generator generator, List<File> templates, List<RootModel> rootModels) {
        super(project, "请稍候 ......", false);
        this.project = project;
        this.windows = windows;
        this.generator = generator;
        this.templates = templates;
        this.rootModels = rootModels;
    }

    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        generator(indicator);
        ContextUtils.refreshProject(project, indicator);
        reformatCode(indicator);
    }

    public void generator(ProgressIndicator indicator) {
        indicator.setText("正在准备数据 ......");
        indicator.setText2("正在准备数据 ......");

        indicator.setIndeterminate(false);
        indicator.setFraction(0.0);

        int modelSize = rootModels.size();
        int templateSize = templates.size();

        double count = (modelSize * templateSize) * 1.0;
        indicator.setText("正在生成代码 ......");
        for (int i = 0; i < modelSize; i++) {
            RootModel rootModel = rootModels.get(i);
            int start = i * templateSize;
            generator.generator(rootModel, templates, (integer, file) -> {
                indicator.setText2(String.format("正在处理 [%s] --> %s", rootModel.getTable().getName(), ContextUtils.getTemplateRelativePath(file)));
                indicator.setFraction((start + integer + 1) / count);
            });
        }
        indicator.setText("生成代码完毕！");
        indicator.setText2("");

        indicator.setFraction(1.0);
        indicator.setIndeterminate(true);
    }

    /**
     * 格式化代码
     *
     * @param indicator 进程指示器
     */
    public void reformatCode(@NotNull ProgressIndicator indicator) {
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
        windows.dispose();
        Messages.showInfoMessage(String.format("代码构建完毕，生成 %s 个文件，涉及 %s 个模板文件。", generator.getSaveFiles().size(), templates.size()), "完成");
    }

    @Override
    public void onThrowable(@NotNull Throwable error) {
        super.onThrowable(error);
        Messages.showErrorDialog("代码生成失败，当前插件 2.x 版本不兼容旧版的代码模板，请升级代码模板，代码模板升级指南请查看插件介绍。\n\n" + error.getMessage(), "生成代码失败");
        windows.setVisible(true);
    }
}
