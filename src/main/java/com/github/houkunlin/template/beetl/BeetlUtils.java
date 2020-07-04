package com.github.houkunlin.template.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.om.AABuilder;
import org.beetl.core.om.DefaultAAFactory;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.util.Map;

/**
 * Beetl 模板引擎
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 5:42
 */
public class BeetlUtils {
    private static final GroupTemplate GROUP_TEMPLATE;

    static {
        //初始化代码
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration configuration;
        try {
            configuration = Configuration.defaultConfiguration();
            GROUP_TEMPLATE = new GroupTemplate(resourceLoader, configuration, GroupTemplate.class.getClassLoader());
            // 必须重新设置，不然因为 ClassLoader 不正确导致渲染错误
            AABuilder.defalutAAFactory = new DefaultAAFactory();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 渲染模板
     *
     * @param templateContent 模板内容
     * @param model           变量
     * @return 渲染结果
     * @throws IOException IO异常
     */
    public static String generatorToString(String templateContent, Map<String, Object> model) throws Exception {
        //获取模板
        Template template = GROUP_TEMPLATE.getTemplate(templateContent);
        template.binding(model);
        //渲染结果
        return template.render();
    }
}
