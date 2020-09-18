package com.github.houkunlin.util;

import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.model.SaveFilePath;
import com.github.houkunlin.template.TemplateUtils;
import com.github.houkunlin.vo.Variable;
import com.github.houkunlin.vo.impl.RootModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.ExceptionUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
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
    private final Map<String, Object> map;
    private final TemplateUtils templateUtils;
    private final List<PsiFile> saveFiles;

    public Generator(Settings settings, Options options, Developer developer) throws IOException {
        this.settings = settings;
        this.options = options;
        this.templateUtils = new TemplateUtils(ContextUtils.getTemplatesPath());
        this.saveFiles = new ArrayList<>();
        this.map = new HashMap<>(8);
        map.put("settings", settings);
        map.put("developer", developer);
        map.put("gen", Variable.getInstance());
        map.put("date", DateTime.now());
    }

    /**
     * 执行生成代码任务
     */
    public void generator(RootModel rootModel, List<File> templateFiles, BiConsumer<Integer, File> progress) {
        if (rootModel == null || templateFiles == null || templateFiles.isEmpty()) {
            return;
        }
        Project project = ContextUtils.getProject();
        map.put("table", rootModel.getTable());
        map.put("columns", rootModel.getColumns());
        map.put("entity", rootModel.getEntity(settings));
        map.put("fields", rootModel.getFields());
        map.put("primary", rootModel.getPrimary());
        for (int i = 0; i < templateFiles.size(); i++) {
            File templateFile = templateFiles.get(i);
            if (progress != null) {
                progress.accept(i, templateFile);
            }
            // 重置内容，方便使用默认配置
            Variable.resetVariables();
            try {
                String result = templateUtils.generatorToString(templateFile, map);
                if (StringUtils.isBlank(result)) {
                    // 不保存空内容的文件
                    continue;
                }
                SaveFilePath saveFilePath;
                if (Variable.type == null) {
                    saveFilePath = new SaveFilePath(templateFile.getName(), settings.getSourcesPathAt("temp"));
                } else {
                    saveFilePath = SaveFilePath.create(rootModel, settings);
                }
                File saveFile = new File(settings.getProjectPath(), String.valueOf(saveFilePath));
                PsiFile psiFile = FileUtils.getInstance().saveFileContent(project, saveFile, result, saveFilePath.isOverride(options));
                if (psiFile != null && !saveFiles.contains(psiFile)) {
                    saveFiles.add(psiFile);
                }
            } catch (Throwable e) {
                ExceptionUtil.rethrow(new RuntimeException("解析错误：" + templateFile.getAbsolutePath() + "\r\n" + e.getMessage(), e));
            }
        }
    }
}
