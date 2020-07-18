package com.github.houkunlin.util;

import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.model.SaveFilePath;
import com.github.houkunlin.template.TemplateUtils;
import com.github.houkunlin.vo.Variable;
import com.github.houkunlin.vo.impl.RootModel;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成工具
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 11:22
 */
public class Generator {
    private final Settings settings;
    private final Options options;
    private final Map<String, Object> map;
    private final TemplateUtils templateUtils;

    public Generator(Settings settings, Options options, Developer developer) throws IOException {
        this.settings = settings;
        this.options = options;
        this.templateUtils = new TemplateUtils(ContextUtils.getTemplatesPath());
        this.map = new HashMap<>(8);
        map.put("settings", settings);
        map.put("developer", developer);
        map.put("gen", Variable.getInstance());
    }

    /**
     * 执行生成代码任务
     *
     * @throws Exception 异常
     */
    public void generator(RootModel rootModel) throws Exception {
        map.put("table", rootModel.getTable());
        map.put("columns", rootModel.getColumns());
        map.put("entity", rootModel.getEntity(settings));
        map.put("fields", rootModel.getFields());
        map.put("primary", rootModel.getPrimary());
        for (File templateFile : ContextUtils.getTemplatesFiles()) {
            // 重置内容，方便使用默认配置
            Variable.resetVariables();
            String result;
            try {
                result = templateUtils.generatorToString(templateFile, map);
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
                autoOverrideSaveContent(result, saveFilePath);
            } catch (Exception e) {
                throw new Exception("解析错误：" + templateFile.getAbsolutePath() + "\r\n" + e.getMessage(), e);
            }
        }
    }

    /**
     * 自动判断是否需要覆盖内容
     *
     * @param saveFilePath 文件路径
     */
    private void autoOverrideSaveContent(String result, SaveFilePath saveFilePath) throws IOException {
        boolean isOverride = false;
        File saveFile = new File(settings.getProjectPath(), String.valueOf(saveFilePath));
        if (saveFile.exists()) {
            if (options.isOverrideJava() && saveFilePath.isJava()) {
                isOverride = true;
            } else if (options.isOverrideXml() && saveFilePath.isXml()) {
                isOverride = true;
            } else if (options.isOverrideOther() && saveFilePath.isOther()) {
                isOverride = true;
            }
            if (isOverride) {
                IO.writeToFile(saveFile, result);
            }
        } else {
            IO.writeToFile(saveFile, result);
        }
    }
}
