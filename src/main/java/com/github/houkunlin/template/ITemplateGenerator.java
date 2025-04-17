package com.github.houkunlin.template;

import java.io.File;
import java.util.Map;

/**
 * 用于标识模板生成器的接口
 *
 * @author daiwenzh5
 */
public interface ITemplateGenerator {

    /**
     * 指定模板文件名，并生成文件
     *
     * @param templateName 模板文件名
     * @param context      上下文
     * @return 生成的文件内容
     * @throws Exception 可能出现的异常
     */
    String generate(String templateName, Map<String, Object> context) throws Exception;

    /**
     * 指定模板内容，并生成文件
     *
     * <p>直接通过传入模板字符串渲染。</p>
     *
     * @param templateContent 模板内容
     * @param context         上下文
     * @return 生成的文件内容
     * @throws Exception 可能出现的异常
     */
    String generateInline(String templateContent, Map<String, Object> context) throws Exception;

    /**
     * 获取工作空间
     *
     * @return 工作空间
     */
    File getWorkspace();
}
