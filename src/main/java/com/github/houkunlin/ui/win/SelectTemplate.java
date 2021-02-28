package com.github.houkunlin.ui.win;

import com.github.houkunlin.ui.win.tree.CheckBoxTreeCellRenderer;
import com.github.houkunlin.ui.win.tree.CheckBoxTreeNode;
import com.github.houkunlin.ui.win.tree.CheckBoxTreeNodeSelectionListener;
import com.github.houkunlin.util.PluginUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模板选择界面
 *
 * @author HouKunLin
 * @date 2020/8/15 0015 16:11
 */
public class SelectTemplate implements IWindows {
    private final CheckBoxTreeNode root;
    /**
     * 模板属性结构数据
     */
    private JTree tree;
    /**
     * 面板：顶级内容面板
     */
    private JPanel content;

    public SelectTemplate() {
        File templatesPath = PluginUtils.getWorkspaceFile(PluginUtils.TEMPLATE_DIR);
        File[] files = templatesPath.listFiles();
        root = new CheckBoxTreeNode("代码模板文件");
        getTreeData(root, files);
        tree.addMouseListener(new CheckBoxTreeNodeSelectionListener());
        tree.setModel(new DefaultTreeModel(root));
        tree.setCellRenderer(new CheckBoxTreeCellRenderer());
        tree.setShowsRootHandles(true);
    }

    private void getTreeData(DefaultMutableTreeNode treeNode, File[] files) {
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            DefaultMutableTreeNode node;
            if (file.isDirectory()) {
                node = new CheckBoxTreeNode(file.getName());
                getTreeData(node, file.listFiles());
            } else {
                // 是一个模板文件
                node = new CheckBoxTreeNode(file);
            }
            treeNode.add(node);
        }
    }

    @Override
    public JPanel getContent() {
        return content;
    }

    public List<File> getAllSelectFile() {
        List<CheckBoxTreeNode> allSelectNodes = root.getAllSelectNodes();
        return allSelectNodes
                .stream()
                .filter(item -> item.getUserObject() instanceof File)
                .map(item -> (File) item.getUserObject())
                .collect(Collectors.toList());
    }
}
