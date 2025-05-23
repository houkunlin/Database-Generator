package com.github.houkunlin.vo.impl;

import com.github.houkunlin.config.Options;
import com.github.houkunlin.vo.IName;
import com.google.common.base.CaseFormat;
import com.intellij.database.model.DasColumn;
import lombok.Getter;

/**
 * Java字段名称对象
 *
 * @author HouKunLin
 */
@Getter
public class FieldNameInfo implements IName {
    private final String value;
    private final String firstUpper;
    private final String firstLower;

    /**
     * @param firstLower 首字母小写
     */
    public FieldNameInfo(String firstLower) {
        this.value = firstLower;
        this.firstUpper = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, value);
        this.firstLower = firstLower;
    }

    public FieldNameInfo(DasColumn dbColumn, Options options) {
        // 把减号替换成下划线
        this.value = options.obtainCaseFormat().to(CaseFormat.LOWER_CAMEL, dbColumn.getName().replaceAll("-", "_"));
        this.firstUpper = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, value);
        this.firstLower = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
