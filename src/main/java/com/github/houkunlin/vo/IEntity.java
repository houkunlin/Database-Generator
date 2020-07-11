package com.github.houkunlin.vo;

import com.github.houkunlin.vo.impl.EntityPackage;

/**
 * 实体类信息
 *
 * @author HouKunLin
 * @date 2020 /5/27 0027 23:54
 */
public interface IEntity {
    /**
     * 获得实体类对象名称（不含后缀）
     *
     * @return 实体类对象名称（不含后缀，驼峰形式，首字母大写）
     */
    IName getName();

    /**
     * 获得实体对象注释内容
     *
     * @return 注释内容
     */
    String getComment();

    /**
     * 获得需要导入的包列表（一般情况下是在实体对象中使用），该方法只返回包的完整名称。
     *
     * @return 包列表
     */
    EntityPackage getPackages();
}
