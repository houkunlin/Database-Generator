package com.github.houkunlin.vo.impl;

import com.github.houkunlin.config.Settings;
import lombok.Getter;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * 实体类对象的包信息
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 15:12
 */
@Getter
public class EntityPackage extends BaseTypeMap<EntityPackageInfo> {
    /**
     * 实体类字段所需要导入的包列表
     */
    private final HashSet<String> list = new HashSet<>();
    private String toString = "";

    public void add(String fullPackageName) {
        if (fullPackageName.startsWith("java.lang.")) {
            return;
        }
        list.add(fullPackageName);
    }


    public void initMore(Settings settings, EntityName entityName) {
        init(settings, item -> new EntityPackageInfo(item.getPackageName(), entityName.get(item.getType())));
    }


    public void clear() {
        list.clear();
        toString = "";
    }

    @Override
    public String toString() {
        if (toString == null || toString.isBlank()) {
            toString = list.stream().map(item -> String.format("import %s;\n", item)).collect(Collectors.joining());
        }
        return toString;
    }

}
