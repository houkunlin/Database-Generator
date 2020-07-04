package com.github.houkunlin.template.velocity;

import com.github.houkunlin.util.IO;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

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
    private static final VelocityEngine ENGINE;

    static {
        Properties properties = new Properties();
        try {
            properties.load(IO.getInputStream("velocity.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ENGINE = new VelocityEngine(properties);
        try {
            ENGINE.init();
        } catch (Exception e) {
            e.printStackTrace();
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
        VelocityContext context = new VelocityContext();
        model.forEach(context::put);

        Writer out = new StringWriter();
        ENGINE.evaluate(context, out, "", templateContent);
        return out.toString();
    }
}
