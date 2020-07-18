package com.github.houkunlin.vo.impl;

import com.github.houkunlin.config.Settings;
import com.github.houkunlin.vo.IName;
import com.google.common.base.CaseFormat;
import com.intellij.database.psi.DbTable;
import lombok.Getter;

/**
 * 实体类名称对象。提供方便直接获取 Entity、Service、ServiceImpl、Dao、Controller 的对象完整名称
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 15:07
 */
@Getter
public class EntityName implements IName {
    private final String value;
    private final String firstUpper;
    private final String firstLower;
    /**
     * 实体类完整名称
     */
    private IName entity;
    /**
     * Service 完整名称
     */
    private IName service;
    /**
     * ServiceImpl 完整名称
     */
    private IName serviceImpl;
    /**
     * Dao 完整名称
     */
    private IName dao;
    /**
     * Controller 完整名称
     */
    private IName controller;

    public EntityName(DbTable dbTable) {
        this.value = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbTable.getName());
        this.firstUpper = value;
        this.firstLower = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, value);
    }

    public EntityName(String name) {
        this.value = name;
        this.firstUpper = value;
        this.firstLower = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, value);
    }

    @Override
    public String toString() {
        return value;
    }

    public void initMore(Settings settings) {
        this.entity = build(settings.getEntitySuffix());
        this.service = build(settings.getServiceSuffix());
        this.serviceImpl = build(settings.getServiceSuffix() + "Impl" );
        this.dao = build(settings.getDaoSuffix());
        this.controller = build(settings.getControllerSuffix());
    }

    private IName build(String suffix) {
        return new EntityNameInfo(value, suffix);
    }
}