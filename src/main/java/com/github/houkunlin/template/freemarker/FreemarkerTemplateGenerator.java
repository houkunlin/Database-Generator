package com.github.houkunlin.template.freemarker;

import com.github.houkunlin.template.AbstractScriptedTemplateGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author daiwenzh5
 * @since 1.0
 */
public class FreemarkerTemplateGenerator extends AbstractScriptedTemplateGenerator {

    private final Configuration configuration;

    public FreemarkerTemplateGenerator(File workspace) throws IOException {
        super(workspace);
        // 把freemarker的jar包添加到工程中
        // 创建一个Configuration对象
        configuration = new Configuration(Configuration.VERSION_2_3_30);
        // 设置config的默认字符集。一般是utf-8
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(getTemplateDir());
    }

    @Override
    protected String doGenerate(String templateName, Map<String, Object> context) throws Exception {
        var template = configuration.getTemplate(templateName);
        var out = new StringWriter();
        template.process(context, out);
        return out.toString();
    }

    @Override
    protected String doGenerateInline(String templateContent, Map<String, Object> context) throws Exception {
        var template = new Template(null, new StringReader(templateContent), configuration);
        var out = new StringWriter();
        template.process(context, out);
        return out.toString();
    }

}
