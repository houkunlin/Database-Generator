package com.github.houkunlin.template;

import com.github.houkunlin.template.freemarker.FreemarkerUtils;
import com.github.houkunlin.template.velocity.VelocityUtils;
import com.github.houkunlin.util.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 模板工具
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 1:35
 */
public class TemplateUtils {

    public static void generator(String content, Object model, File saveFile, boolean cover) throws IOException {
    }

    /**
     * 渲染模板
     *
     * @param templateFile 模板内容
     * @param model        变量信息
     * @return 渲染结果
     * @throws IOException IO异常
     */
    public static String generatorToString(File templateFile, Map<String, Object> model) throws Exception {
        return generatorToString(IO.read(new FileInputStream(templateFile)), model, TplType.create(templateFile));
    }

    /**
     * 渲染模板
     *
     * @param templateContent 模板内容
     * @param model           变量
     * @return 渲染结果
     * @throws IOException IO异常
     */
    public static String generatorToString(String templateContent, Map<String, Object> model, TplType type) throws Exception {
        switch (type) {
            case NONE:
                break;
            case BEETL:
                break;
            case VELOCITY:
                return VelocityUtils.generatorToString(templateContent, model);
            case FREEMARKER:
                return FreemarkerUtils.generatorToString(templateContent, model);
            default:
        }
        return templateContent;
    }
}
