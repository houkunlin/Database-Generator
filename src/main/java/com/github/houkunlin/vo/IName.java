package com.github.houkunlin.vo;

/**
 * 名称处理
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 12:26
 */
public interface IName {

    /**
     * 获得名称
     *
     * @return 名称（驼峰形式，首字母小写）
     */
    default String firstLower() {
        return toString();
    }

    /**
     * 获得名称
     *
     * @return 名称（驼峰形式，首字母大写）
     */
    default String firstUpper() {
        return toString();
    }

    /**
     * 获得变量名称
     *
     * @return 变量名称
     */
    default String var() {
        return firstLower();
    }
}
