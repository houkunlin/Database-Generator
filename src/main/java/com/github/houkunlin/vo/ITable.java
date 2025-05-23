package com.github.houkunlin.vo;

/**
 * 数据库表信息
 *
 * @author HouKunLin
 */
public interface ITable {
    /**
     * 获得数据库表名称
     *
     * @return 数据库表名称
     */
    String getName();

    /**
     * 获得数据库表注释内容
     *
     * @return 注释内容
     */
    String getComment();
}
