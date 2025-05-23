package com.github.houkunlin.vo;

/**
 * 名称处理
 *
 * @author HouKunLin
 */
public interface IName {

    /**
     * 获得名称
     *
     * @return 名称（驼峰形式，首字母小写）
     */
    default String getFirstLower() {
        return toString();
    }

    /**
     * 获得名称
     *
     * @return 名称（驼峰形式，首字母大写）
     */
    default String getFirstUpper() {
        return toString();
    }
}
