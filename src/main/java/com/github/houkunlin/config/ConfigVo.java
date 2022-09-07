package com.github.houkunlin.config;

import com.github.houkunlin.model.TableColumnType;
import lombok.Data;

@Data
public class ConfigVo {
    private static ConfigVo INSTANCE;
    private Developer developer;
    private Options options;
    private Settings settings;
    private TableColumnType[] types;

    public static ConfigVo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigVo();
            INSTANCE.developer = new Developer();
            INSTANCE.options = new Options();
            INSTANCE.settings = new Settings();
            INSTANCE.types = new TableColumnType[0];
        }
        return INSTANCE;
    }
}
