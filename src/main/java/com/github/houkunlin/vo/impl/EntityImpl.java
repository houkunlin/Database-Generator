package com.github.houkunlin.vo.impl;

import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.vo.IEntity;
import com.intellij.database.psi.DbTable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

/**
 * 实体类信息
 *
 * @author HouKunLin
 */
@Getter
public class EntityImpl implements IEntity {
    private final EntityPackage packages = new EntityPackage();
    private EntityName name;
    @Setter
    private String comment;
    @Setter
    private String uri;

    public EntityImpl(DbTable dbTable, Options options) {
        this.comment = Objects.toString(dbTable.getComment(), "");
        this.name = new EntityName(dbTable, options);
    }

    public void setName(String name) {
        this.name = new EntityName(name);
    }

    /**
     * 初始化更多的信息
     *
     * @param fullTypeNames 字段类型名称列表
     * @param settings      设置信息对象（用来初始化包名信息）
     */
    public void initMore(Set<String> fullTypeNames, Settings settings) {
        packages.clear();
        fullTypeNames.forEach(packages::add);
        name.initMore(settings);
        packages.initMore(settings, name);
    }
}
