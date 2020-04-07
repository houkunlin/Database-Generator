package com.github.houkunlin.ui;

import com.github.houkunlin.model.JTableModel;
import com.github.houkunlin.model.Table;
import com.github.houkunlin.model.TableColumnType;
import com.intellij.database.psi.DbTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

/**
 * 插件的TabUI
 *
 * @author HouKunLin
 * @date 2020/4/6 0006 23:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TableInfoTabUI extends TabUI {
    private final DbTable dbTable;
    private JTableModel model;
    private Table tableInfo;

    public TableInfoTabUI(DbTable dbTable, TableColumnType[] tableColumnTypes) {
        super();
        this.dbTable = dbTable;

        model = new JTableModel(getJTable(), dbTable);
        tableInfo = model.getTableInfo();

        tableInfo.setColumnTypes(tableColumnTypes);

        getTableNameField().setText(tableInfo.getTableName());
        getEntityNameField().setText(tableInfo.getEntityName());
        getCommentField().setText(tableInfo.getComment());
    }

    /**
     * 重新获取实体类名称
     */
    public void saveTableInfo() {
        tableInfo.setEntityName(getEntityNameField().getText());
        tableInfo.setComment(StringUtils.defaultString(getCommentField().getText(), ""));
    }
}
