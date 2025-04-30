package com.github.houkunlin.ui.win;

import com.github.houkunlin.config.Options;
import com.github.houkunlin.util.PluginUtils;
import com.github.houkunlin.vo.impl.RootModel;
import com.intellij.database.psi.DbTable;
import com.intellij.psi.PsiElement;
import lombok.Data;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表配置面板（涵盖多个数据库表的内容）
 *
 * @author HouKunLin
 * @date 2020/8/15 0015 16:10
 */
@Data
public class TableSetting implements IWindows {
    /**
     * 面板：顶级内容面板
     */
    private JPanel content;
    /**
     * 标签：包含多个数据库表的标签界面
     */
    private JTabbedPane tableTabbedPane;
    private PsiElement[] psiElements;
    private List<TablePanel> tablePanels = new ArrayList<>();
    private Runnable initRunnable;

    public TableSetting(PsiElement[] psiElements, Options options) {
        this.psiElements = psiElements;
        initRunnable = () -> {
            // 确保即时加载types信息
            PluginUtils.resetColumnTypes();
            for (PsiElement psiElement : psiElements) {
                if (!(psiElement instanceof DbTable dbTable)) {
                    continue;
                }
                var tablePanel = new TablePanel(dbTable, options);
                tableTabbedPane.addTab(dbTable.getName(), tablePanel.getContent());
                tablePanels.add(tablePanel);
            }
        };
        initRunnable.run();
    }

    /**
     * 重置配置面板
     */
    public void reset() {
        tableTabbedPane.removeAll();
        tablePanels.clear();
        initRunnable.run();
    }

    public List<RootModel> getRootModels() {
        List<RootModel> rootModels = new ArrayList<>();
        for (TablePanel tablePanel : tablePanels) {
            rootModels.add(tablePanel.toModel());
        }
        return rootModels;
    }

    @Override
    public JPanel getContent() {
        return content;
    }
}
