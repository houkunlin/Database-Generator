package com.github.houkunlin.template.beetl;

import com.github.houkunlin.util.ScriptManager;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Beetl 模板引擎
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 5:42
 */
public class BeetlUtils {
    private final GroupTemplate groupTemplateString;
    private final GroupTemplate groupTemplateFile;

    public BeetlUtils(File templateRootPath) throws IOException {
        //初始化代码
        Configuration configuration = Configuration.defaultConfiguration();
        configuration.setNativeCall(true);
        configuration.setNativeSecurity("org.beetl.core.DefaultNativeSecurityManager");
        groupTemplateString = createGroupTemplate(new StringTemplateResourceLoader(), configuration);
        groupTemplateFile = createGroupTemplate(new FileResourceLoader(templateRootPath.getAbsolutePath()), configuration);
    }

    private GroupTemplate createGroupTemplate(ResourceLoader loader, Configuration configuration) {
        GroupTemplate groupTemplate = new GroupTemplate(loader, configuration, GroupTemplate.class.getClassLoader());
        groupTemplate.setErrorHandler(new BeetlErrorHandler());
        return groupTemplate;
    }

    /**
     * 渲染模板
     *
     * @param templateFile 模板文件
     * @param model        变量
     * @return 渲染结果
     * @throws IOException IO异常
     */
    public String generatorFileToString(String templateFile, Map<String, Object> model) throws Exception {
        //获取模板
        Template template = groupTemplateFile.getTemplate(templateFile);
        resetScriptsConfig(model);
        template.binding(model);
        //渲染结果
        return template.render();
    }

    private void resetScriptsConfig(Map<String, Object> model) {
        var scriptManager = model.get(ScriptManager.VARIABLE);
        if (!(scriptManager instanceof ScriptManager)) {
            return;
        }
        model.remove(ScriptManager.VARIABLE);
        ((ScriptManager) scriptManager).forEach(this::registerScriptMethods);
    }

    private void registerScriptMethods(String name, Object script) {
        groupTemplateFile.registerFunctionPackage(ScriptManager.NAMESPACE + name, script);
    }


    /**
     * 渲染模板
     *
     * @param templateContent 模板内容
     * @param model           变量
     * @return 渲染结果
     * @throws IOException IO异常
     */
    public String generatorToString(String templateContent, Map<String, Object> model) throws Exception {
        //获取模板
        Template template = groupTemplateString.getTemplate(templateContent);
        resetScriptsConfig(model);
        template.binding(model);
        //渲染结果
        return template.render();
    }
}
