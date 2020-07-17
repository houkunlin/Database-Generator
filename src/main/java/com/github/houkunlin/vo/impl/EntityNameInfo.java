package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.IName;
import com.google.common.base.CaseFormat;
import lombok.Getter;

/**
 * 实体类名称信息对象
 *
 * @author HouKunLin
 * @date 2020/7/18 0018 3:00
 */
@Getter
public class EntityNameInfo implements IName {
    private final String value;
    private final String firstUpper;
    private final String firstLower;

    public EntityNameInfo(String entityName, String suffix) {
        this.value = entityName + suffix;
        this.firstLower = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, toString());
        this.firstUpper = value;
    }

    @Override
    public String toString() {
        return value;
    }
}