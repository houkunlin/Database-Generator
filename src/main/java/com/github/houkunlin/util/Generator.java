package com.github.houkunlin.util;

import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.model.SaveFilePath;
import com.github.houkunlin.model.Table;
import com.github.houkunlin.template.TemplateUtils;
import com.github.houkunlin.template.freemarker.TemplateAction;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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
    private final AtomicReference<String> filename = new AtomicReference<>();
    private final AtomicReference<String> filepath = new AtomicReference<>();
    private final AtomicReference<String> type = new AtomicReference<>();

    public Generator(Settings settings, Options options, Developer developer) {
        this.settings = settings;
        this.options = options;

        this.map = new HashMap<>(8);
        map.put("settings", settings);
        map.put("developer", developer);
        map.put("gen", new TemplateAction(filename::set, filepath::set, type::set));
    }

    /**
     * 执行生成代码任务
     *
     * @throws Exception 异常
     */
    public void generator(Table table) throws Exception {
        map.put("table", table);
        for (File templateFile : ContextUtils.getTemplatesFiles()) {
            // 重置内容，方便使用默认配置
            filename.set(null);
            filepath.set(null);
            type.set(null);
            String result;
            try {
                result = TemplateUtils.generatorToString(templateFile, map);
                result = "";
                SaveFilePath saveFilePath;
                if (type.get() == null) {
                    saveFilePath = new SaveFilePath(filename, filepath,
                            templateFile.getName(), settings.getSourcesPathAt("temp"));
                } else {
                    saveFilePath = SaveFilePath.create(filename, filepath, type, table, settings);
                }
//                System.out.println("模板文件：" + templateFile + "，渲染结果保存到：" + saveFilePath);
                autoOverrideSaveContent(result, saveFilePath);
            } catch (Exception e) {
                e.printStackTrace();
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
