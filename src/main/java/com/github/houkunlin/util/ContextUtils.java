package com.github.houkunlin.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.File;

/**
 * 插件上下文工具
 *
 * @author HouKunLin
 * @date 2020/4/6 0006 19:22
 */
public class ContextUtils {
    private static Project project;
    /**
     * 项目本地配置路径
     */
    private static File localConfigPath;
    /**
     * 项目本地模板路径
     */
    private static File templatesPath;

    public static void setProject(Project project) {
        ContextUtils.project = project;
        localConfigPath = new File(project.getBasePath(), "generator");
        templatesPath = new File(localConfigPath, "templates");

        mkdirs(localConfigPath);
        mkdirs(templatesPath);
    }

    /**
     * 创建目录信息
     *
     * @param path 路径
     */
    private static void mkdirs(File path) {
        if (!path.exists()) {
            if (!path.mkdirs()) {
                Messages.showMessageDialog("创建\"" + path + "\"路径失败", "创建路径失败", Messages.getErrorIcon());
            }
        }
    }

    public static Project getProject() {
        return project;
    }

    public static File getLocalConfigPath() {
        return localConfigPath;
    }

    public static File getTemplatesPath() {
        return templatesPath;
    }

    /**
     * 获取本地配置文件
     *
     * @param filename 文件名称
     * @return 文件对象
     */
    public static File getLocalConfigFile(String filename) {
        if (localConfigPath == null) {
            return null;
        }
        return new File(localConfigPath, filename);
    }

    /**
     * 获取本地模板文件列表
     *
     * @return 文件列表
     */
    public static File[] getTemplatesFiles() {
        if (templatesPath == null) {
            return new File[0];
        }
        return templatesPath.listFiles();
    }
}
