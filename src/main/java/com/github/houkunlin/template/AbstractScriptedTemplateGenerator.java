package com.github.houkunlin.template;

import com.github.houkunlin.util.PluginUtils;
import com.github.houkunlin.util.ScriptManager;
import lombok.Getter;

import java.io.File;
import java.util.Map;

/**
 * 可执行脚本的模板生成器
 *
 * @author daiwenzh5
 * @since 1.0
 */
@Getter
public abstract class AbstractScriptedTemplateGenerator implements ITemplateGenerator {

    /**
     * 工作空间
     */
    private final File workspace;

    /**
     * 模板目录
     */
    private final File templateDir;

    public AbstractScriptedTemplateGenerator(File workspace) {
        this.workspace = workspace;
        this.templateDir = workspace.toPath()
                                    .resolve(PluginUtils.TEMPLATE_DIR)
                                    .toFile();
    }

    @Override
    public final String generate(String templateName, Map<String, Object> context) throws Exception {
        var scriptManager = ScriptManager.of(workspace.toPath()
                                                      .resolve(ScriptManager.DIR), true);
        registerScriptManager(context, scriptManager);
        return doGenerate(templateName, context);
    }

    @Override
    public final String generateInline(String templateContent, Map<String, Object> context) throws Exception {
        var scriptManager = ScriptManager.of(workspace.toPath()
                                                      .resolve(ScriptManager.DIR), true);
        registerScriptManager(context, scriptManager);
        return doGenerateInline(templateContent, context);
    }

    /**
     * 将{@link ScriptManager}注册到模板引擎的上下文中
     *
     * @param context       上下文
     * @param scriptManager 脚本管理器
     */
    protected void registerScriptManager(Map<String, Object> context, ScriptManager scriptManager) {
        context.put(ScriptManager.VARIABLE, scriptManager);
    }

    /**
     * 根据传入的模板名称，读取模板，并执行生成逻辑
     *
     * @param templateName 模板名称
     * @param context      上下文
     * @return 生成的代码
     * @throws Exception 可能出现的异常
     */
    protected abstract String doGenerate(String templateName, Map<String, Object> context) throws Exception;

    /**
     * 根据传入的模板内容，直接执行生成逻辑
     *
     * @param templateContent 模板内容
     * @param context         上下文
     * @return 生成的代码
     * @throws Exception 可能出现的异常
     */
    protected abstract String doGenerateInline(String templateContent, Map<String, Object> context) throws Exception;

}
