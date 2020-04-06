package com.github.houkunlin.ui;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

/**
 * 数据库表的 Tab UI 界面
 *
 * @author HouKunLin
 * @date 2020/4/6 0006 23:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TabUI extends JPanel {
    private JTextField tableNameField;
    private JTextField entityNameField;
    private JTable jTable;
    private final Dimension fieldDimension = new Dimension(150, 24);

    public TabUI() {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        setLayout(layout);
        buildRow1();
        buildRow2();
    }

    private void buildRow1() {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        JPanel jPanel = new JPanel(layout);

        tableNameField = new JTextField();
        tableNameField.setPreferredSize(fieldDimension);
        tableNameField.setEditable(false);
        jPanel.add(new JLabel("表名："));
        jPanel.add(tableNameField);

        entityNameField = new JTextField();
        entityNameField.setPreferredSize(fieldDimension);
        jPanel.add(new JLabel("Entity 名称："));
        jPanel.add(entityNameField);

        jPanel.setPreferredSize(new Dimension(950, 35));
        add(jPanel);
    }

    public void buildRow2() {
        jTable = new JTable();
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane jScrollPane = new JScrollPane(jTable, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(940, 220));
        jTable.setRowHeight(30);

        add(jScrollPane);
    }
}
