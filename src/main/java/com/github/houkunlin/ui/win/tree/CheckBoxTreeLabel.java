package com.github.houkunlin.ui.win.tree;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * 复选框的树形节点展示label
 *
 * @author HouKunLin
 * @date 2020/8/17 0017 11:05
 */
public class CheckBoxTreeLabel extends JLabel {
    @Setter
    @Getter
    private boolean selected;
    @Setter
    @Getter
    private boolean hasFocus;

    public CheckBoxTreeLabel() {
    }

    @Override
    public void setBackground(Color color) {
        if (color instanceof ColorUIResource)
            color = null;
        super.setBackground(color);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension retDimension = super.getPreferredSize();
        if (retDimension != null) {
            retDimension = new Dimension(retDimension.width + 3, retDimension.height);
        }
        return retDimension;
    }
}
