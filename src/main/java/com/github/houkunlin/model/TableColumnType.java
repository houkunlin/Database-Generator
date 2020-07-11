package com.github.houkunlin.model;

import lombok.Data;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字段类型数据
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 16:52
 */
@Data
public class TableColumnType {
    public static final TableColumnType DEFAULT = new TableColumnType(true);
    /**
     * 数据库对应的类型
     */
    private List<String> dbTypes;
    /**
     * 短名称
     */
    private String shortName;
    /**
     * 长名称
     */
    private String longName;
    /**
     * 是否是默认的类型
     */
    private boolean isDefault = false;

    private TableColumnType(boolean isDefault) {
        this.isDefault = isDefault;
        if (isDefault) {
            this.shortName = "Object";
            this.longName = "java.lang.Object";
        }
    }

    /**
     * 判断一个数据库类型是否在这个对象里面
     *
     * @param dbType 数据库字段类
     * @return 判断结果
     */
    public boolean at(String dbType) {
        if (dbTypes == null) {
            return false;
        }
        if (dbTypes.contains(dbType)) {
            return true;
        }
        for (String type : dbTypes) {
            Matcher matcher = Pattern.compile(type).matcher(dbType);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

}
