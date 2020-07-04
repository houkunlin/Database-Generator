package com.github.houkunlin.vo;

/**
 * 数据库表字段信息（数据库表列对象信息）
 *
 * @author HouKunLin
 * @date 2020/5/27 0027 23:55
 */
public interface ITableColumn {
    /**
     * 获得字段名称
     *
     * @return 字段名称
     */
    String getName();

    /**
     * 获得字段注释内容
     *
     * @return 注释内容
     */
    String getComment();

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
