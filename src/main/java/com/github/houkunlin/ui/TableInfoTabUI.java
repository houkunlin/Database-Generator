package com.github.houkunlin.ui;

import com.github.houkunlin.model.JTableModel;
import com.github.houkunlin.vo.impl.EntityImpl;
import com.github.houkunlin.vo.impl.RootModel;
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
    /**
     * 数据库表对象
     */
    private final DbTable dbTable;
    /**
     * 界面表格对象的数据模型
     */
    private JTableModel model;

    private RootModel rootModel;

    public TableInfoTabUI(DbTable dbTable) {
        super();
        this.dbTable = dbTable;

        model = new JTableModel(getJTable(), dbTable);
        toModel();
    }

    public RootModel toModel() {
        if (rootModel == null) {
            rootModel = new RootModel(dbTable, model.getFieldImpls(), model.getColumnImpls());
            getTableNameField().setText(rootModel.getTable().getName());
            getEntityNameField().setText(rootModel.getEntity().getName());
            getCommentField().setText(rootModel.getTable().getComment());
            return rootModel;
        }
        EntityImpl entity = rootModel.getEntity();
        entity.setName(getEntityNameField().getText());
        entity.setComment(StringUtils.defaultString(getCommentField().getText(), ""));
        return rootModel;
    }
}
