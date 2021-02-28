package com.github.houkunlin.template.velocity;

import com.github.houkunlin.util.IO;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * Velocity 模板工具
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 3:49
 */
public class VelocityUtils {

    private final VelocityEngine engine;

    public VelocityUtils(File rootPath) throws IOException {
        Properties properties = new Properties();
        properties.load(IO.getInputStream("velocity.properties"));
        engine = new VelocityEngine(properties);
        engine.setProperty("file.resource.loader.path", rootPath.getAbsolutePath());
        engine.init();
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
        VelocityContext context = new VelocityContext(model);
        Template template = engine.getTemplate(templateFile);
        Writer out = new StringWriter();
        template.merge(context, out);
        return out.toString();
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
        VelocityContext context = new VelocityContext(model);
        Writer out = new StringWriter();
        engine.evaluate(context, out, "", templateContent);
        return out.toString();
    }
}
