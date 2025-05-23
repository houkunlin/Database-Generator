package com.github.houkunlin.vo;

import com.intellij.database.model.DataType;

/**
 * 数据库表字段信息（数据库表列对象信息）
 *
 * @author HouKunLin
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
     * 获得数据库的字段数据类型（IDEA内置对象）
     *
     * @return 字段类型信息
     */
    DataType getDataType();

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
}
