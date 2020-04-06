package com.github.houkunlin.action;

import com.github.houkunlin.ui.ActionUIRunnable;
import com.github.houkunlin.util.ContextUtils;
import com.github.houkunlin.util.SyncResources;
import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;

/**
 * 操作入口
 *
 * @author HouKunLin
 * @date 2020/4/6 0006 18:08
 */
public class MainAction extends AnAction {

    /**
     * 在 Database 面板中右键打开插件主页面
     *
     * @param actionEvent 操作对象
     */
    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        PsiElement[] psiElements = actionEvent.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        if (psiElements == null || psiElements.length == 0) {
            Messages.showMessageDialog("请选择一张表", "通知", Messages.getInformationIcon());
            return;
        }
        if (psiElements.length > 1) {
            Messages.showMessageDialog("请选择一张表(暂时无法处理多表)", "通知", Messages.getInformationIcon());
            return;
        }
        for (PsiElement psiElement : psiElements) {
            if (!(psiElement instanceof DbTable)) {
                Messages.showMessageDialog("请选择一张表", "通知", Messages.getInformationIcon());
                return;
            }
        }
        Project project = actionEvent.getData(PlatformDataKeys.PROJECT);
        if (project == null) {
            Messages.showMessageDialog("无法获取到当前项目的 Project 对象", "错误", Messages.getErrorIcon());
            return;
        }
        ContextUtils.setProject(project);
        new SyncResources().run();
        new ActionUIRunnable(actionEvent).run();
    }
}
