package com.github.houkunlin.template.velocity;

import com.github.houkunlin.template.AbstractScriptedTemplateGenerator;
import com.github.houkunlin.util.IO;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * 基于velocity的模板生成器
 *
 * @author daiwenzh5
 * @since 1.0
 */
public class VelocityTemplateGenerator extends AbstractScriptedTemplateGenerator {

    private final VelocityEngine engine;

    public VelocityTemplateGenerator(File workspace) throws IOException {
        super(workspace);
        var properties = new Properties();
        properties.load(IO.getInputStream("velocity.properties"));
        engine = new VelocityEngine(properties);
        engine.setProperty("resource.loader.file.path", getTemplateDir().getAbsolutePath());
        engine.init();
    }

    @Override
    protected String doGenerate(String templateName, Map<String, Object> context_) {
        var context = new VelocityContext(context_);
        var template = engine.getTemplate(templateName);
        var out = new StringWriter();
        template.merge(context, out);
        return out.toString();
    }

    @Override
    protected String doGenerateInline(String templateContent, Map<String, Object> context_) throws Exception {
        var context = new VelocityContext(context_);
        var out = new StringWriter();
        engine.evaluate(context, out, "", templateContent);
        return out.toString();
    }

}
