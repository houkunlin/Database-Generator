package com.github.houkunlin.ui;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主页面板
 * private static final Logger LOG = Logger.getInstance(EditorColorsManagerImpl.class);
 *
 * @author HouKunLin
 * @date 2020/4/2 0002 21:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActionUI extends JFrame {
    private JPanel jpanel;
    private JButton selectProjectPathButton;
    /**
     * 项目路径
     */
    private JTextField projectPathField;
    /**
     * Java代码路径
     */
    private JTextField javaPathField;
    /**
     * 资源文件路径
     */
    private JTextField sourcesPathField;
    /**
     * Entity 后缀
     */
    private JTextField entitySuffixField;
    /**
     * Controller 后缀
     */
    private JTextField controllerSuffixField;
    /**
     * Dao 后缀
     */
    private JTextField daoSuffixField;
    /**
     * Service 后缀
     */
    private JTextField serviceSuffixField;
    /**
     * Entity 包名
     */
    private JTextField entityPackageField;
    /**
     * Dao 包名
     */
    private JTextField daoPackageField;
    /**
     * Service 包名
     */
    private JTextField servicePackageField;
    /**
     * Controller 包名
     */
    private JTextField controllerPackageField;
    /**
     * Mapper 包名
     */
    private JTextField xmlPackageField;

    private JButton selectEntityPackageButton;
    private JButton selectDaoPackageButton;
    private JButton selectServicePackageButton;
    private JButton selectControllerPackageButton;
    private JTable columnTable;
    private JTabbedPane tabbedPane;
    /**
     * 数据库表名
     */
    private JTextField tableNameField;
    /**
     * Entity 名称
     */
    private JTextField entityNameField;
    /**
     * 完成事件按钮
     */
    private JButton finishButton;
    private JCheckBox overrideJavaCheckBox;
    private JCheckBox overrideXmlCheckBox;
    private JCheckBox overrideOtherCheckBox;
    private JTextField authorField;
    private JTextField emailField;

    public ActionUI() {
        super("MyBatis Plus 插件");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(jpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        int frameWidth = jpanel.getPreferredSize().width;
        int frameHeight = jpanel.getPreferredSize().height;
        setLocation((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2);
        pack();
        setVisible(true);
        tabbedPane.setTitleAt(0, "构建选项");
        tabbedPane.setTitleAt(1, "字段信息");
//        initTable();
        columnTable.setRowHeight(30);
//        setAlwaysOnTop(true);
    }

    public void showWindows() {
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
    }

    private void initTable() {
        columnTable.setModel(new MyTable());
        TableColumn column = columnTable.getColumnModel().getColumn(0);
        column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        int width = 30;
        column.setMaxWidth(width);
        column.setMinWidth(width);
    }

    /**
     * 使用此内部类来创建一个表格
     *
     * @author Administrator
     */
    class MyTable extends AbstractTableModel {
        //原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/guava/guava_table.html
        Table<Integer, Integer, Object> table = HashBasedTable.create();


        String[] names = {"选中", "姓名", "年龄", "生日", "薪水"};

        public MyTable() {
            for (int i = 0; i < 50; i++) {
                table.put(i, 0, true);
                for (int j = 1; j < names.length; j++) {
                    table.put(i, j, names[j] + i);
                }
            }
            System.out.println("行数：" + table.rowKeySet().size());
            System.out.println("列数" + table.columnKeySet().size());
        }

        @Override
        public int getRowCount() {
            return table.rowKeySet().size();
        }

        @Override
        public int getColumnCount() {
            return table.columnKeySet().size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return table.get(rowIndex, columnIndex);
        }

        @Override
        public String getColumnName(int col) {
            return names[col];
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return true;
        }

        @Override
        public void setValueAt(Object value, int row, int column) {
            table.put(row, column, value);
            fireTableCellUpdated(row, column);
        }
    }

    public static void main(String[] args) {
        ActionUI ui = new ActionUI();
        ui.initTable();
        ui.finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.dispose();
            }
        });
    }
}
