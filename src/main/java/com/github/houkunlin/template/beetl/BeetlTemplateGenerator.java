package com.github.houkunlin.template.beetl;

import com.github.houkunlin.template.AbstractScriptedTemplateGenerator;
import com.github.houkunlin.util.ScriptManager;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author daiwenzh5
 * @since 1.0
 */
public class BeetlTemplateGenerator extends AbstractScriptedTemplateGenerator {

    private final GroupTemplate groupTemplateString;
    private final GroupTemplate groupTemplateFile;

    public BeetlTemplateGenerator(File workspace) throws IOException {
        super(workspace);
        //初始化代码
        var configuration = Configuration.defaultConfiguration();
        configuration.setNativeCall(true);
        configuration.setNativeSecurity("org.beetl.core.DefaultNativeSecurityManager");
        groupTemplateString = createGroupTemplate(new StringTemplateResourceLoader(), configuration);
        groupTemplateFile = createGroupTemplate(new FileResourceLoader(getTemplateDir().getAbsolutePath()), configuration);

    }

    private GroupTemplate createGroupTemplate(ResourceLoader<?> loader, Configuration configuration) {
        var groupTemplate = new GroupTemplate(loader, configuration, GroupTemplate.class.getClassLoader());
        groupTemplate.setErrorHandler(new BeetlErrorHandler());
        return groupTemplate;
    }

    @Override
    protected String doGenerate(String templateName, Map<String, Object> context) {
        //获取模板
        var template = groupTemplateFile.getTemplate(templateName);
        template.binding(context);
        //渲染结果
        return template.render();
    }

    @Override
    protected String doGenerateInline(String templateContent, Map<String, Object> context) {
        //获取模板
        var template = groupTemplateString.getTemplate(templateContent);
        template.binding(context);
        //渲染结果
        return template.render();
    }

    @Override
    protected void registerScriptManager(Map<String, Object> context, ScriptManager scriptManager) {
        scriptManager.forEach(this::registerScriptMethods);
    }

    private void registerScriptMethods(String name, Object script) {
        groupTemplateFile.registerFunctionPackage(ScriptManager.NAMESPACE + name, script);
    }
}
