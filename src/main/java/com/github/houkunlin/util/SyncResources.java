package com.github.houkunlin.util;

import com.intellij.xml.actions.xmlbeans.FileUtils;
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
    private String initFilename = "init.properties";

    /**
     * 复制插件内部的模板文件到项目路径中
     */
    @Override
    public void run() {
        File initFile = ContextUtils.getLocalConfigPath(initFilename);
        if (initFile == null) {
            return;
        }
        boolean initFileExists = initFile.exists();
        try {
            // 强制覆盖初始化文件
            FileUtils.saveStreamContentAsFile(initFile.getAbsolutePath(), IO.getInputStream(initFilename));
        } catch (Exception ignore) {
        }
        if (initFileExists) {
            return;
        }
        try {
            copyConfig();
            copyTemplate();
        } catch (Exception ignore) {
        }
    }

    /**
     * 复制插件内部的配置文件到项目路径中
     *
     * @throws IOException 复制异常
     */
    private void copyConfig() throws IOException {
        File localConfigPath = ContextUtils.getLocalConfigPath();
        if (localConfigPath == null) {
            return;
        }
        String[] filenames = new String[]{"config/types.json", "config/options.json", "config/settings.json", "config/developer.json"};
        for (String filename : filenames) {
            InputStream inputStream = IO.getInputStream(filename);
            if (inputStream == null) {
                continue;
            }
            FileUtils.saveStreamContentAsFile(localConfigPath.getAbsolutePath() + File.separator + filename, inputStream);
        }
    }

    /**
     * 复制插件内部的代码模板到项目路径中
     *
     * @throws IOException 复制异常
     */
    private void copyTemplate() throws IOException {
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
            FileUtils.saveStreamContentAsFile(ContextUtils.getTemplatesPath() + File.separator + filePath.replace("templates/", ""), inputStream);
        }
    }
}
