package com.github.houkunlin.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ExceptionUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 同步插件资源到项目路径
 *
 * @author HouKunLin
 * @date 2020/4/6 0006 19:10
 */
@Data
public class SyncResources implements Runnable {
    /**
     * 插件初始化文件
     */
    private static final String INIT_FILENAME = "init.properties";
    private Project project = PluginUtils.getProject();

    /**
     * 复制插件内部的模板文件到项目路径中
     */
    @Override
    public void run() {
        checkOldVersion();
        File initFile = PluginUtils.getExtensionDirFile(INIT_FILENAME);
        boolean initFileExists = initFile.exists();
        if (initFileExists) {
            // 不再强制覆盖 初始化文件
            return;
        }
        try {
            // 只处理不存在的初始化文件
            String content = IO.readResources(INIT_FILENAME);
            FileUtils.copyFile(project, initFile, content, false);
        } catch (Throwable e) {
            ExceptionUtil.rethrow(new RuntimeException("同步插件文件到本地出现错误：\r\n" + e.getMessage(), e));
        }
        try {
            syncFiles();
        } catch (Throwable e) {
            ExceptionUtil.rethrow(new RuntimeException("同步插件文件到本地出现错误：\r\n" + e.getMessage(), e));
        }

        PluginUtils.refreshWorkspace();
    }

    private void checkOldVersion() {
        final File initFile = PluginUtils.getProjectDirFile(INIT_FILENAME);
        if (initFile.exists()) {
            int dialog = Messages.showYesNoDialog(project, "在当前项目路径下发现 generator/init.properties 配置文件，请问是否需要把 generator/ 迁移到 .idea/generator/ 路径？\n\n我们建议您应该这样操作！", "插件配置迁移", Messages.getQuestionIcon());
            if (dialog == 0) {
                if (!PluginUtils.getProjectDir().renameTo(PluginUtils.getProjectWorkspaceDir())) {
                    Messages.showWarningDialog("请手动把 generator/ 迁移到 .idea/generator/ 路径！", "迁移失败");
                } else {
                    PluginUtils.refreshProject();
                }
            }
        }
    }

    /**
     * 复制插件内部的代码模板到项目路径中
     *
     * @throws IOException 复制异常
     */
    private void syncFiles() throws IOException {
        String syncFiles = IO.readResources("syncFiles.txt");
        String[] split = syncFiles.split("\n");
        for (String filePath : split) {
            if (StringUtils.isBlank(filePath)) {
                continue;
            }
            InputStream inputStream = IO.getInputStream(filePath);
            if (inputStream == null) {
                continue;
            }
            String content = IO.read(inputStream);

            FileUtils.copyFile(project, PluginUtils.getExtensionDirFile(filePath), content, true);
        }
    }
}
