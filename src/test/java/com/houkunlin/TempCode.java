package com.houkunlin;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.extensions.PluginId;

public class TempCode {
    public void test() {
        // 获得插件信息
        PluginManagerCore.getPlugin(PluginId.getId("com.github.houkunlin.database.generator"));
        // 获得所有插件列表
        PluginManager.getPlugins();
        // 获得IDEA项目配置路径
        PathManager.getConfigPath();

        // TODO 把 ContextUtils/ReadJsonConfig/SyncResources 整合到一个工具对象中，重构代码
        /*
         * TODO 把代码模板文件放到插件的临时文件（PathManager.getConfigPath() + "extensions/${pluginId}"）路径中，
         * 这样可以多个项目共用同一套配置、代码模板，而不用在不同项目直接复制来保持代码模板一致
         */
        /*
         * TODO 同时兼容原来的项目路径下的配置文件，优先获取这些配置
         */
    }
}
