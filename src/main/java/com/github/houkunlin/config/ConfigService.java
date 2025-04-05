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
import org.mapstruct.factory.Mappers;

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
    public static final BeanTransform BEAN_TRANSFORM = Mappers.getMapper(BeanTransform.class);
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

    public synchronized void refresh(){
        ConfigVo configVo = PluginUtils.loadConfig();
        Developer developer = configVo.getDeveloper();
        Options options = configVo.getOptions();
        Settings settings = configVo.getSettings();
        assert developer != null;
        assert options != null;
        assert settings != null;
        if (settings.getProjectPath() == null) {
            settings.setProjectPath(PluginUtils.getProjectPath().normalize().toString());
        }
        BEAN_TRANSFORM.copyTo(developer, this.developer);
        BEAN_TRANSFORM.copyTo(options, this.options);
        BEAN_TRANSFORM.copyTo(settings, this.settings);
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
