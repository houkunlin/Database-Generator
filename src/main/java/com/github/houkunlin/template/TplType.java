package com.github.houkunlin.template;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * 代码模板类型
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 2:55
 */
public enum TplType {
    /**
     * 未知模板
     */
    NONE,
    /**
     * Freemarker 模板
     */
    FREEMARKER,
    /**
     * BEETL 模板
     */
    BEETL,
    /**
     * VELOCITY 模板
     */
    VELOCITY,
    ;

    public static TplType create(File file) {
        return create(file.getName());
    }

    public static TplType create(String file) {
        String extension = FilenameUtils.getExtension(file);
        if (StringUtils.isBlank(extension)) {
            return NONE;
        }
        switch (extension) {
            case "ftl":
                return FREEMARKER;
            case "vm":
                return VELOCITY;
            case "btl":
                return BEETL;
            default:
                return NONE;
        }
    }
}
