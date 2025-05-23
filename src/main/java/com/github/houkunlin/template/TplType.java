package com.github.houkunlin.template;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * 代码模板类型
 *
 * @author HouKunLin
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
        if (extension == null) {
            return NONE;
        }
        return switch (extension) {
            case "ftl" -> FREEMARKER;
            case "vm" -> VELOCITY;
            case "btl" -> BEETL;
            default -> NONE;
        };
    }

}
