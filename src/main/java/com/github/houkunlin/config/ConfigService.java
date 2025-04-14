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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private final List<String> lastSelectionTemplates = new ArrayList<>();

    public ConfigService() {
        ConfigVo configVo = PluginUtils.loadConfig();
        developer = configVo.getDeveloper();
        options = configVo.getOptions();
        settings = configVo.getSettings();
        assert developer != null;
        assert options != null;
        assert settings != null;
    }

    /**
     * 重新从配置文件中读取配置信息
     */
    public synchronized void reset(){
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

    /**
     * 设置上次选择的模板列表，仅当启用 "记住上次选择的模板" 选项时，才生效
     *
     * @param lastSelectionTemplates 上次选择的模板列表
     */
    public void setLastSelectionTemplates(List<String> lastSelectionTemplates) {
        if (this.options == null || !this.options.isRetainLastSelectionTemplates()) {
            return;
        }
        this.lastSelectionTemplates.clear();
        this.lastSelectionTemplates.addAll(lastSelectionTemplates);
    }

    /**
     * 获取上次选择的模板列表， 当未启用 "记住上次选择的模板" 选项时，直接返回空列表
     * @return 上次选择的模板列表
     */
    public List<String> getLastSelectionTemplates() {
        if (this.options == null || !this.options.isRetainLastSelectionTemplates()) {
            return Collections.emptyList();
        }
        return this.lastSelectionTemplates;
    }
}
