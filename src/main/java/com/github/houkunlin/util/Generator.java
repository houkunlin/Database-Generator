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
    private final List<PsiFile> saveFiles;
    private final Project project;
    private final Map<File, TemplateUtils> templates = new HashMap<>();

    public Generator(Settings settings, Options options, Developer developer) {
        this.project = PluginUtils.getProject();
        this.settings = settings;
        this.options = options;
        this.saveFiles = new ArrayList<>();
        this.map = new HashMap<>(8);
        map.put("settings", settings);
        map.put("developer", developer);
        map.put("gen", Variable.getInstance());
        map.put("date", DateTime.now());
    }

    private File getTemplateWorkspace(File templateFile) {
        final String absolutePath = templateFile.getAbsolutePath();
        File file = PluginUtils.getProjectWorkspacePluginDir();
        if (absolutePath.startsWith(file.getAbsolutePath())) {
            return file;
        }
        file = PluginUtils.getProjectPluginDir();
        if (absolutePath.startsWith(file.getAbsolutePath())) {
            return file;
        }
        file = PluginUtils.getExtensionPluginDir();
        if (absolutePath.startsWith(file.getAbsolutePath())) {
            return file;
        }
        throw new RuntimeException("无法找到代码模板文件在插件中的根路径：" + templateFile.getAbsolutePath());
    }

    private TemplateUtils getTemplateUtils(File templateWorkspace) {
        TemplateUtils utils = templates.get(templateWorkspace);
        if (utils == null) {
            final File templateRoot = new File(templateWorkspace, PluginUtils.TEMPLATE_DIR);
            try {
                utils = new TemplateUtils(templateRoot);
                templates.put(templateWorkspace, utils);
            } catch (IOException e) {
                throw new RuntimeException("创建 Root 模板处理器失败：" + templateRoot.getAbsolutePath() + "\r\n" + e.getMessage(), e);
            }
        }
        return utils;
    }

    /**
     * 执行生成代码任务
     */
    public void generator(RootModel rootModel, List<File> templateFiles, BiConsumer<Integer, String> progress) {
        if (rootModel == null || templateFiles == null || templateFiles.isEmpty()) {
            return;
        }
        map.put("table", rootModel.getTable());
        map.put("columns", rootModel.getColumns());
        map.put("entity", rootModel.getEntity(settings));
        map.put("fields", rootModel.getFields());
        map.put("primary", rootModel.getPrimary());
        for (int i = 0; i < templateFiles.size(); i++) {
            File templateFile = templateFiles.get(i);

            final File templateWorkspace = getTemplateWorkspace(templateFile);
            String templateFilename = FileUtils.relativePath(templateWorkspace, templateFile).replaceFirst(PluginUtils.TEMPLATE_DIR, "");
            if (progress != null) {
                progress.accept(i, templateFilename);
            }
            // 重置内容，方便使用默认配置
            Variable.resetVariables();
            generatorTemplateFile(rootModel, getTemplateUtils(templateWorkspace), templateFile, templateFilename);
        }
    }

    private void generatorTemplateFile(RootModel rootModel, TemplateUtils templateUtils, File templateFile, String templateFilename) {
        try {
            String result = templateUtils.generatorFileToString(templateFilename, map);
            if (StringUtils.isBlank(result)) {
                // 不保存空内容的文件
                return;
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
