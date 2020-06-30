package com.github.houkunlin.config;

import lombok.Data;

/**
 * 构建参数
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 21:57
 */
@Data
public class Options {
    /**
     * 覆盖Java文件
     */
    private boolean overrideJava = true;
    /**
     * 覆盖XML文件
     */
    private boolean overrideXml = true;
    /**
     * 覆盖其他文件
     */
    private boolean overrideOther = true;

}
