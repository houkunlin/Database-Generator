package com.github.houkunlin.ui.win.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 拥有复选框的树形节点
 *
 * @author HouKunLin
 * @date 2020/8/17 0017 11:04
 */
@SuppressWarnings("serial")
public class CheckBoxTreeNode extends DefaultMutableTreeNode {
    protected boolean selected;

    public CheckBoxTreeNode() {
        this(null);
    }

    public CheckBoxTreeNode(Object userObject) {
        this(userObject, true, false);
    }

    public CheckBoxTreeNode(Object userObject, boolean allowsChildren, boolean selected) {
        super(userObject, allowsChildren);
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            // 如果选中，则将其所有的子结点都选中
            if (children != null) {
                for (Object obj : children) {
                    CheckBoxTreeNode node = (CheckBoxTreeNode) obj;
                    if (!node.isSelected()) {
                        node.setSelected(true);
                    }
                }
            }
            // 向上检查，如果父结点的所有子结点都被选中，那么将父结点也选中
            CheckBoxTreeNode pNode = (CheckBoxTreeNode) parent;
            // 开始检查pNode的所有子节点是否都被选中
            if (pNode != null) {
                int index = 0;
                for (; index < pNode.children.size(); ++index) {
                    CheckBoxTreeNode pChildNode = (CheckBoxTreeNode) pNode.children.get(index);
                    if (!pChildNode.isSelected()) {
                        break;
                    }
                }

				  /*表明pNode所有子结点都已经选中，则选中父结点，
				  该方法是一个递归方法，因此在此不需要进行迭代，因为
				  当选中父结点后，父结点本身会向上检查的。*/

                if (index == pNode.children.size()) {
                    if (!pNode.isSelected()) {
                        pNode.setSelected(true);
                    }
                }
            }
        } else {

			 /* 如果是取消父结点导致子结点取消，那么此时所有的子结点都应该是选择上的；
			  否则就是子结点取消导致父结点取消，然后父结点取消导致需要取消子结点，但
			  是这时候是不需要取消子结点的。*/

            if (children != null) {
                int index = 0;
                for (; index < children.size(); ++index) {
                    CheckBoxTreeNode childNode = (CheckBoxTreeNode) children.get(index);
                    if (!childNode.isSelected()) {
                        break;
                    }
                }
                // 从上向下取消的时候
                if (index == children.size()) {
                    for (javax.swing.tree.TreeNode child : children) {
                        CheckBoxTreeNode node = (CheckBoxTreeNode) child;
                        if (node.isSelected()) {
                            node.setSelected(false);
                        }
                    }
                }
            }

            // 向上取消，只要存在一个子节点不是选上的，那么父节点就不应该被选上。
            //这一块注释之后，子节点的取消不会影响父节点的勾选，为实现取消子节点，单独只选择父节点自己查看
            CheckBoxTreeNode pNode = (CheckBoxTreeNode) parent;
            if (pNode != null && pNode.isSelected()) {
                pNode.setSelected(false);
            }
        }
    }

    public List<CheckBoxTreeNode> getAllSelectNodes() {
        List<CheckBoxTreeNode> list = new ArrayList<>();
        if (selected) {
            list.add(this);
        }
        if (children != null) {
            for (TreeNode child : children) {
                if (child instanceof CheckBoxTreeNode) {
                    CheckBoxTreeNode node = (CheckBoxTreeNode) child;
                    list.addAll(node.getAllSelectNodes());
                }
            }
        }
        return list;
    }

    @Override
    public String toString() {
        if (userObject == null) {
            return "";
        }
        if (userObject instanceof File) {
            return ((File) userObject).getName();
        } else {
            return userObject.toString();
        }
    }
}
