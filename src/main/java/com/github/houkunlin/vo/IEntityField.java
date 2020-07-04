package com.github.houkunlin.vo;

/**
 * 实体类字段信息
 *
 * @author HouKunLin
 * @date 2020/5/27 0027 23:54
 */
public interface IEntityField {
    /**
     * 获得字段名称（变量名称）
     *
     * @return 字段名称（驼峰形式，首字母小写）
     */
    String getName();

    /**
     * 设置字段名称（变量名称）
     *
     * @param name 字段名称（变量名称）
     */
    void setName(String name);

    /**
     * 字段变量名称（首字母小写）
     *
     * @return 变量名（不含后缀，驼峰形式，首字母小写）
     */
    default String getVariableName() {
        return getNameFirstLower();
    }

    /**
     * 获得字段名称（变量名称）
     *
     * @return 字段名称（驼峰形式，首字母小写）
     */
    String getNameFirstLower();

    /**
     * 获得字段名称（变量名称）
     *
     * @return 字段名称（驼峰形式，首字母大写）
     */
    String getNameFirstUpper();

    /**
     * 获得字段注释内容
     *
     * @return 注释内容
     */
    String getComment();

    /**
     * 设置字段注释内容
     *
     * @param comment 字段注释内容
     */
    void setComment(String comment);

    /**
     * 获得字段类型名称（短名称）
     *
     * @return 字段类型名称（短名称）
     */
    String getTypeName();

    /**
     * 获得字段类型名称（长名称、完整名称）
     *
     * @return 字段类型名称（长名称、完整名称）
     */
    String getFullTypeName();

    /**
     * 获取是否是主键字段
     *
     * @return 是否是主键
     */
    boolean isPrimaryKey();

    /**
     * 获取是否选中该字段
     *
     * @return 是否选中该字段
     */
    boolean isSelected();

    /**
     * 设置是否选中该字段
     *
     * @param selected 是否选中
     */
    void setSelected(boolean selected);
}
