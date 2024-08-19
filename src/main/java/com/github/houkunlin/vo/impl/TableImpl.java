package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.ITable;
import com.intellij.database.psi.DbTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * 数据库表信息
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 0:59
 */
@Getter
public class TableImpl implements ITable {
    /**
     * 数据库表的原始对象
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final DbTable dbTable;

    private final String name;
    private final String comment;

    public TableImpl(DbTable dbTable) {
        this.dbTable = dbTable;
        this.name = dbTable.getName();
        this.comment = Objects.toString(dbTable.getComment(), "");
    }
}
