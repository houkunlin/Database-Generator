package com.github.houkunlin.util;

import com.github.houkunlin.config.ConfigService;
import com.github.houkunlin.config.OtherConfig;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.util.ExceptionUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        File initFile = PluginUtils.getExtensionPluginDirFile(INIT_FILENAME);
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
        OtherConfig otherConfig = null;
        final ConfigService configService = ConfigService.getInstance(project);
        if (configService != null) {
            otherConfig = configService.getOtherConfig();
            if (!otherConfig.isShowUpgradeMoveTip()) {
                return;
            }
        }

        final File projectPluginDir = PluginUtils.getProjectPluginDir();
        final File projectWorkspacePluginDir = PluginUtils.getProjectWorkspacePluginDir();
        final File initFile1 = new File(projectPluginDir, INIT_FILENAME);
        final File initFile2 = new File(projectWorkspacePluginDir, INIT_FILENAME);
        if (initFile1.exists() && !initFile2.exists()) {
            int dialog = Messages.showYesNoDialog(project,
                    "在当前项目路径下发现 generator/init.properties 配置文件，请问是否需要把 generator/ 迁移到 .idea/generator/ 路径？\n\n我们建议您应该这样操作！",
                    "插件配置迁移",
                    Messages.getQuestionIcon(),
                    getDoNotAskOption(otherConfig));
            if (dialog == 0) {
                // 当 projectWorkspacePluginDir.exists() == false 时，projectWorkspacePluginDir.listFiles() == null
                final File[] listFiles = projectWorkspacePluginDir.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    // 此时 projectWorkspacePluginDir 路径一定存在，并且路径下一定存在文件
                    Messages.showWarningDialog(".idea/generator/ 路径下存在文件，请手动把 generator/ 迁移到 .idea/generator/ 路径！", "迁移失败");
                    return;
                }
                if (projectWorkspacePluginDir.exists()) {
                    // 此时 projectWorkspacePluginDir 一定是一个空的文件夹，可以直接删除
                    projectWorkspacePluginDir.delete();
                }
                if (!projectPluginDir.renameTo(projectWorkspacePluginDir)) {
                    Messages.showWarningDialog("请手动把 generator/ 迁移到 .idea/generator/ 路径！", "迁移失败");
                } else {
                    if (projectPluginDir.exists()) {
                        projectPluginDir.delete();
                    }
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

            FileUtils.copyFile(project, PluginUtils.getExtensionPluginDirFile(filePath), content, true);
        }
    }

    @Nullable
    private DialogWrapper.DoNotAskOption getDoNotAskOption(OtherConfig otherConfig) {
        if (otherConfig == null) {
            return null;
        }
        return new DialogWrapper.DoNotAskOption() {
            @Override
            public boolean isToBeShown() {
                return otherConfig.isShowUpgradeMoveTip();
            }

            @Override
            public void setToBeShown(boolean toBeShown, int exitCode) {
                otherConfig.setShowUpgradeMoveTip(toBeShown);
            }

            @Override
            public boolean canBeHidden() {
                return true;
            }

            @Override
            public boolean shouldSaveOptionsOnCancel() {
                return true;
            }

            @Override
            public @NotNull
            @NlsContexts.Checkbox String getDoNotShowMessage() {
                return "下次不再提示此消息";
            }
        };
    }
}
