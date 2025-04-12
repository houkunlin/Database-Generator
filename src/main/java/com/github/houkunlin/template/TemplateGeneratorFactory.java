package com.github.houkunlin.template;

import com.github.houkunlin.template.beetl.BeetlTemplateGenerator;
import com.github.houkunlin.template.freemarker.FreemarkerTemplateGenerator;
import com.github.houkunlin.template.velocity.VelocityTemplateGenerator;
import com.github.houkunlin.util.PluginUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.File;
import java.io.IOException;

/**
 * 模板生成器工厂
 *
 * @author daiwenzh5
 * @since 1.0
 */
public class TemplateGeneratorFactory {

    private static final Table<String, TplType, ITemplateGenerator> cache = HashBasedTable.create();

    /**
     * 创建模板生成器
     *
     * @param templateFile 模板文件
     * @return 模板生成器
     */
    public static ITemplateGenerator create(File templateFile) {
        var workspace = getTemplateWorkspace(templateFile);
        var workspacePath = workspace.getAbsolutePath();
        var type = TplType.create(templateFile);
        var value = cache.get(workspacePath, type);
        if (value != null) {
            return value;
        }
        var generator = create(workspace, type);
        cache.put(workspacePath, type, generator);
        return generator;
    }

    private static ITemplateGenerator create(File workspace, TplType type) {
        try {
            return switch (type) {
                case BEETL -> new BeetlTemplateGenerator(workspace);
                case FREEMARKER -> new FreemarkerTemplateGenerator(workspace);
                case VELOCITY -> new VelocityTemplateGenerator(workspace);
                default -> null;
            };
        } catch (IOException e) {
            throw new RuntimeException("创建 Root 模板处理器失败：" + workspace.getAbsolutePath() + "\r\n" + e.getMessage(), e);
        }
    }

    private static File getTemplateWorkspace(File templateFile) {
        var absolutePath = templateFile.getAbsolutePath();
        var file = PluginUtils.getProjectWorkspacePluginDir();
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
}
