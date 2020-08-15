package com.github.houkunlin.ui.win;

import javax.swing.*;

/**
 * 模板选择界面
 *
 * @author HouKunLin
 * @date 2020/8/15 0015 16:11
 */
public class SelectTemplate implements IWindows {
    /**
     * 模板属性结构数据
     */
    private JTree tree;
    /**
     * 面板：顶级内容面板
     */
    private JPanel content;

    @Override
    public JPanel getContent() {
        return content;
    }
}
