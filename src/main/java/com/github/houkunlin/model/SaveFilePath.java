package com.github.houkunlin.model;

import com.github.houkunlin.config.Settings;
import com.github.houkunlin.vo.Variable;
import com.github.houkunlin.vo.impl.RootModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存文件信息
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 20:52
 */
public class SaveFilePath {

    /**
     * 模板文件的 type 类型
     */
    private final String type;
    /**
     * 模板文件保存路径
     */
    private String toString;

    @Getter
    private final boolean override;

    public SaveFilePath(String filename, String filepath, boolean override) {
        String name = getValue(Variable.filename, filename);
        String path = getValue(Variable.filepath, filepath);
        type = Variable.type;
        toString = (path.replace(".", "/") + "/" + name);
        toString = toString.replace("\\", "/").replaceAll("/+", "/");
        this.override = override;
        Variable.resetVariables();
    }

    public static void resetVariables() {
        Variable.filename = null;
        Variable.filepath = null;
        Variable.type = null;
    }

    public static SaveFilePath create(RootModel rootModel, Settings settings) {
        var entityName = String.valueOf(rootModel.getEntity().getName());
        if (Variable.type == null) {
            return createTemp(entityName, settings);
        }
        return settings.getFileTypes()
                       .stream()
                       .filter(item -> item.getType()
                                           .equals(Variable.type))
                       .map(item -> createSaveFilePath(settings, entityName, item))
                       .findFirst()
                       .orElseGet(() -> createTemp(entityName, settings));
    }

    private static @NotNull SaveFilePath createSaveFilePath(Settings settings, String entityName, FileType item) {
        var filename = entityName + item.getSuffix() + item.getExt();
        var relativePath = settings.getPathAt(item.getPath(), item.getPackageName());
        return new SaveFilePath(filename, relativePath, item.isOverride());
    }

    private String getValue(String tempValue, String defaultValue) {
        if (tempValue != null) {
            return tempValue;
        } else {
            return defaultValue;
        }
    }

    @Override
    public String toString() {
        return toString;
    }

    public static SaveFilePath createTemp(String filename, Settings settings) {
        return new SaveFilePath(filename, settings.getResourcesPathAt("temp"), true);
    }
}
