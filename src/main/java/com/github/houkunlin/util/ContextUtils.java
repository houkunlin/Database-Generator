package com.github.houkunlin.util;

import com.github.houkunlin.model.TableColumnType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 插件上下文工具
 *
 * @author HouKunLin
 * @date 2020/4/6 0006 19:22
 */
@Getter
public class ContextUtils {
    /**
     * 当前项目对象
     */
    private static Project project;
    /**
     * 项目本地配置路径
     */
    private static File localConfigPath;
    /**
     * 项目本地模板路径
     */
    private static File templatesPath;
    /**
     * 字段类型映射
     */
    private static TableColumnType[] columnTypes;

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

    public static void setProject(Project project) {
        ContextUtils.project = project;
        localConfigPath = new File(project.getBasePath(), "generator");
        templatesPath = new File(localConfigPath, "templates");
        columnTypes = null;

        mkdirs(localConfigPath);
        // 创建配置文件路径
        mkdirs(new File(localConfigPath, "config"));
        // 创建模板文件路径
        mkdirs(templatesPath);
    }

    public static File getLocalConfigPath() {
        return localConfigPath;
    }

    public static File getTemplatesPath() {
        return templatesPath;
    }

    public static TableColumnType[] getColumnTypes() {
        if (columnTypes == null) {
            columnTypes = ReadJsonConfig.getTableColumnTypes();
        }
        return columnTypes;
    }

    /**
     * 获取模板文件的相对路径
     *
     * @param templateFile 模板文件
     * @return 模板文件的相对路径
     */
    public static String getTemplateRelativePath(@NonNull File templateFile) {
        String absolutePath = templatesPath.getAbsolutePath();
        String fileAbsolutePath = templateFile.getAbsolutePath();
        if (fileAbsolutePath.startsWith(absolutePath)) {
            return fileAbsolutePath.substring(absolutePath.length() + 1).replace("\\", "/");
        }
        return fileAbsolutePath.replace("\\", "/");
    }

    /**
     * 获取本地配置文件
     *
     * @param filename 文件名称
     * @return 文件对象
     */
    public static File getLocalConfigPath(String filename) {
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
    public static List<File> getTemplatesFiles() {
        if (templatesPath == null) {
            return Collections.emptyList();
        }
        return getDirAllFiles(templatesPath);
    }

    /**
     * 获得所有模板文件列表
     *
     * @param path 模板根文件对象
     * @return 模板文件列表
     */
    private static List<File> getDirAllFiles(File path) {
        List<File> files = new ArrayList<>();
        for (File file : Objects.requireNonNull(path.listFiles())) {
            if (file.isFile()) {
                files.add(file);
            } else {
                files.addAll(getDirAllFiles(file));
            }
        }
        return files;
    }
}
