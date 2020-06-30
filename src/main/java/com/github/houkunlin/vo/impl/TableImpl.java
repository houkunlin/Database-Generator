package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.ITable;
import com.intellij.database.psi.DbTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 数据库表信息
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 0:59
 */
@Data
public class TableImpl implements ITable {
    /**
     * 数据库表的原始对象
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final DbTable dbTable;

    private String name;
    private String comment;
}
