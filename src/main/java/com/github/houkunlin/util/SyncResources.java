package com.github.houkunlin.util;

import com.intellij.xml.actions.xmlbeans.FileUtils;
import lombok.Data;

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
        File initFile = new File(ContextUtils.getLocalConfigPath(), initFilename);
        if (initFile.exists()) {
            try {
                // 强制覆盖初始化文件
                IO.writeToFile(ContextUtils.getLocalConfigFile(initFilename), IO.getInputStream(initFilename));
            } catch (Exception ignore) {
            }
            return;
        }
        try {
            copyResourcesToProject(ContextUtils.getLocalConfigPath(), "", "init.properties", "types.json", "options.json", "settings.json", "developer.json");
            copyResourcesToProject(ContextUtils.getTemplatesPath(), "templates/", "Controller.ftl", "Entity.ftl", "Repository.ftl", "Service.ftl", "ServiceImpl.ftl", "Xml.ftl", "MyBatisUtils.java");
        } catch (Exception ignore) {
        }
    }


    /**
     * 复制插件资源到本地项目中
     *
     * @param resourcesPath 路径
     * @param filenames     文件名称
     * @throws IOException 异常
     */
    private void copyResourcesToProject(File savePath, String resourcesPath, String... filenames) throws IOException {
        for (String filename : filenames) {
            InputStream inputStream = IO.getInputStream(resourcesPath + filename);
            if (inputStream == null) {
                continue;
            }
            FileUtils.saveStreamContentAsFile(savePath.getAbsolutePath() + File.separator + filename, inputStream);
        }
    }
}
