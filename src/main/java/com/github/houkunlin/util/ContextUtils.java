package com.github.houkunlin.util;

import com.github.houkunlin.model.TableColumnType;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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

    /**
     * 获取本地配置文件
     *
     * @param filename 文件名称
     * @return 文件对象
     */
    @NotNull
    public static File getLocalConfigPath(String filename) {
        File file = new File(localConfigPath, filename);
        mkdirs(file.getParentFile());
        return file;
    }

    public static File getTemplatesPath() {
        return templatesPath;
    }

    @NotNull
    public static File getTemplatesPath(String filename) {
        File file = new File(templatesPath, filename);
        mkdirs(file.getParentFile());
        return file;
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

    /**
     * 刷新项目
     */
    public static void refreshProject() {
        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Refresh Project ...") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                Consumer<VirtualFile> refresh = (virtualFile) -> {
                    if (virtualFile != null) {
                        virtualFile.refresh(false, true);
                    }
                };
                refresh.accept(LocalFileSystem.getInstance().findFileByPath(Objects.requireNonNull(project.getBasePath())));
                refresh.accept(project.getProjectFile());
                refresh.accept(project.getWorkspaceFile());
            }
        });
    }
}
