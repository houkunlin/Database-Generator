package com.github.houkunlin.template.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.om.AABuilder;
import org.beetl.core.om.DefaultAAFactory;
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
        groupTemplateString = createGroupTemplate(new StringTemplateResourceLoader(), configuration);
        groupTemplateFile = createGroupTemplate(new FileResourceLoader(templateRootPath.getAbsolutePath()), configuration);
        // 必须重新设置，不然因为 ClassLoader 不正确导致渲染错误
        AABuilder.defalutAAFactory = new DefaultAAFactory();
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
        template.binding(model);
        //渲染结果
        return template.render();
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
        template.binding(model);
        //渲染结果
        return template.render();
    }
}
