package com.github.houkunlin.config;

import com.github.houkunlin.util.PluginUtils;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 配置 Service
 *
 * @author HouKunLin
 * @date 2020/8/18 0018 17:17
 */
@Data
@State(name = "com.github.houkunlin.database.generator.config.ConfigService",
    defaultStateAsResource = true,
    storages = {@Storage("database-generator-config.xml")})
public class ConfigService implements PersistentStateComponent<ConfigService> {
    private Developer developer;
    private Options options;
    private Settings settings;

    public ConfigService() {
        ConfigVo configVo = PluginUtils.loadConfig();
        developer = configVo.getDeveloper();
        options = configVo.getOptions();
        settings = configVo.getSettings();
        assert developer != null;
        assert options != null;
        assert settings != null;
    }

    @Nullable
    public static ConfigService getInstance(Project project) {
        return project.getService(ConfigService.class);
    }

    @Nullable
    @Override
    public ConfigService getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ConfigService state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
