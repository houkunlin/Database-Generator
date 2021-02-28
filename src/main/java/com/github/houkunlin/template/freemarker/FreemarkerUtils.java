package com.github.houkunlin.template.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

/**
 * 模板工具类
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 16:14
 */
public class FreemarkerUtils {
    private final Configuration configuration;

    public FreemarkerUtils(File rootPath) throws IOException {
        // 把freemarker的jar包添加到工程中
        // 创建一个Configuration对象
        configuration = new Configuration(Configuration.VERSION_2_3_30);
        // 设置config的默认字符集。一般是utf-8
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(rootPath);
    }

    /**
     * 渲染模板
     *
     * @param templateFile 模板文件
     * @param model        变量
     * @return 渲染结果
     * @throws IOException       IO异常
     * @throws TemplateException 渲染模板失败
     */
    public String generatorFileToString(String templateFile, Object model) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templateFile);
        Writer out = new StringWriter();
        template.process(model, out);
        return out.toString();
    }

    /**
     * 渲染模板
     *
     * @param templateContent 模板内容
     * @param model           变量
     * @return 渲染结果
     * @throws IOException       IO异常
     * @throws TemplateException 渲染模板失败
     */
    public String generatorToString(String templateContent, Object model) throws IOException, TemplateException {
        Template template = new Template(null, new StringReader(templateContent), configuration);
        Writer out = new StringWriter();
        template.process(model, out);
        return out.toString();
    }
}
