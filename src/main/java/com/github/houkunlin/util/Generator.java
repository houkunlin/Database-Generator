package com.github.houkunlin.util;

import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.model.SaveFilePath;
import com.github.houkunlin.template.ITemplateGenerator;
import com.github.houkunlin.template.TemplateGeneratorFactory;
import com.github.houkunlin.vo.Variable;
import com.github.houkunlin.vo.impl.RootModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.ExceptionUtil;
import lombok.Data;
import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 代码生成工具
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 11:22
 */
@Data
public class Generator {
    private final Settings settings;
    private final Options options;
    private final Map<String, Object> context;
    private final List<PsiFile> saveFiles;
    private final Project project;

    public Generator(Settings settings, Options options, Developer developer) {
        this.project = PluginUtils.getProject();
        this.settings = settings;
        this.options = options;
        this.saveFiles = new ArrayList<>();
        this.context = new HashMap<>(8);
        context.put("settings", settings);
        context.put("developer", developer);
        context.put("gen", Variable.getInstance());
        context.put("date", DateTime.now());
    }

    /**
     * 执行生成代码任务
     */
    public void generator(RootModel rootModel, List<File> templateFiles, BiConsumer<Integer, String> progress) {
        if (rootModel == null || templateFiles == null || templateFiles.isEmpty()) {
            return;
        }
        loadContext(rootModel);
        for (int i = 0; i < templateFiles.size(); i++) {
            var templateFile = templateFiles.get(i);
            var generator = TemplateGeneratorFactory.create(templateFile);
            var workspace = generator.getWorkspace();
            var templateFilename = FileUtils.relativePath(workspace, templateFile)
                                            .replaceFirst(PluginUtils.TEMPLATE_DIR, "");
            if (progress != null) {
                progress.accept(i, templateFilename);
            }
            // 重置内容，方便使用默认配置
            Variable.resetVariables();
            generatorTemplateFile(rootModel, generator, templateFile, templateFilename);
        }
    }

    private void loadContext(RootModel rootModel) {
        context.put("table", rootModel.getTable());
        context.put("columns", rootModel.getColumns());
        context.put("entity", rootModel.getEntity(settings));
        context.put("fields", rootModel.getFields());
        context.put("primary", rootModel.getPrimary());
    }

    private void generatorTemplateFile(RootModel rootModel, ITemplateGenerator generator, File templateFile, String templateFilename) {
        try {
            var result = generator.generate(templateFilename, context);
            if (result == null || result.isBlank()) {
                // 不保存空内容的文件
                return;
            }
            var savePsiFile = getSavePsiFile(rootModel, templateFile, result);
            if (savePsiFile != null && !saveFiles.contains(savePsiFile)) {
                saveFiles.add(savePsiFile);
            }
        } catch (Throwable e) {
            ExceptionUtil.rethrow(new RuntimeException("解析错误：" + templateFile.getAbsolutePath() + "\r\n" + e.getMessage(), e));
        }
    }

    private PsiFile getSavePsiFile(RootModel rootModel, File templateFile, String result) {
        var saveFilePath = getSaveFilePath(rootModel, templateFile);
        var saveFile = new File(settings.getProjectPath(), String.valueOf(saveFilePath));
        return FileUtils.getInstance()
                        .saveFileContent(project, saveFile, result, saveFilePath.isOverride());
    }

    private SaveFilePath getSaveFilePath(RootModel rootModel, File templateFile) {
        if (Variable.type == null) {
            return SaveFilePath.createTemp(templateFile.getName(), settings);
        }
        return SaveFilePath.create(rootModel, settings);
    }
}
