package com.github.houkunlin.ui.win.tree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 复选框树形节点鼠标点击监听事件
 *
 * @author HouKunLin
 * @date 2020/8/17 0017 11:05
 */
public class CheckBoxTreeNodeSelectionListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent event) {
        JTree tree = (JTree) event.getSource();
        int row = tree.getRowForLocation(event.getX(), event.getY());
        TreePath path = tree.getPathForRow(row);
        if (path != null) {
            CheckBoxTreeNode node = (CheckBoxTreeNode) path.getLastPathComponent();
            if (node != null) {
                boolean isSelected = !node.isSelected();
                node.setSelected(isSelected);
                ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(node);
            }
        }
    }
}
