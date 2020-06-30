package com.github.houkunlin.template.freemarker;

import com.github.houkunlin.util.IO;
import com.intellij.xml.actions.xmlbeans.FileUtils;
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
    private static final Configuration configuration;

    static {
        // 把freemarker的jar包添加到工程中
        //创建一个Configuration对象
        configuration = new Configuration();
        // 设置config的默认字符集。一般是utf-8
        configuration.setDefaultEncoding("utf-8");
    }

    public static void generator(String content, Object model, File saveFile) throws IOException, TemplateException {
        Template template = new Template(null, new StringReader(content), configuration);

        File tempFile = File.createTempFile("idea-mybatis-plus-plugin", ".gen.code");
        Writer out = new FileWriter(tempFile);
        template.process(model, out);
        FileUtils.copyFile(tempFile, saveFile);
    }

    /**
     * 渲染模板
     *
     * @param templateFile 模板内容
     * @param model        变量信息
     * @return 渲染结果
     * @throws IOException       IO异常
     * @throws TemplateException 渲染模板失败
     */
    public static String generatorToString(File templateFile, Object model) throws IOException, TemplateException {
        return generatorToString(IO.read(new FileInputStream(templateFile)), model);
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
    public static String generatorToString(String templateContent, Object model) throws IOException, TemplateException {
        Template template = new Template(null, new StringReader(templateContent), configuration);
        Writer out = new StringWriter();
        template.process(model, out);
        return out.toString();
    }
}
