package com.github.houkunlin.util;

import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.model.TableColumnType;
import com.google.gson.Gson;
import com.intellij.openapi.ui.Messages;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 读取配置信息
 *
 * @author HouKunLin
 * @date 2020/4/6 0006 18:53
 */
public class ReadJsonConfig {
    private static Gson gson = new Gson();

    /**
     * 获取设置信息
     *
     * @return 设置信息
     */
    public static Settings getSettings() {
        try {
            return parseJson(Settings.class, "settings.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Settings();
    }

    /**
     * 获取参数信息
     *
     * @return 参数信息
     */
    public static Options getOptions() {
        try {
            return parseJson(Options.class, "options.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Options();
    }

    /**
     * 获取开发者信息
     *
     * @return 开发者信息
     */
    public static Developer getDeveloper() {
        try {
            return parseJson(Developer.class, "developer.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Developer();
    }

    /**
     * 获取数据库字段类型映射配置
     *
     * @return 数据库字段类型映射配置
     */
    public static TableColumnType[] getTableColumnTypes() {
        try {
            return parseJson(TableColumnType[].class, "types.json");
        } catch (Exception e) {
            e.printStackTrace();
            Messages.showMessageDialog(e.getMessage(), "解析默认类型映射配置失败(严重影响功能)", Messages.getErrorIcon());
        }
        return new TableColumnType[0];
    }

    /**
     * 从JSON内容中解析字段类型映射关系对象
     *
     * @param tClass    解析的对象
     * @param resources 资源文件路径
     * @return 解析结果
     */
    private static <T> T parse(Class<T> tClass, String resources) {
        String jsonString;
        jsonString = IO.readResources(resources);
        return gson.fromJson(jsonString, tClass);
    }

    /**
     * 从JSON内容中解析字段类型映射关系对象
     *
     * @param tClass      解析的对象
     * @param inputStream 资源输入流
     * @return 解析结果
     */
    private static <T> T parse(Class<T> tClass, InputStream inputStream) {
        String jsonString;
        jsonString = IO.read(inputStream);
        return gson.fromJson(jsonString, tClass);
    }

    /**
     * 从JSON内容中解析字段类型映射关系对象
     *
     * @param tClass    转换类型
     * @param resources 资源名称
     * @param <T>       类型对象
     * @return 类型对象
     */
    private static <T> T parseJson(Class<T> tClass, String resources) {
        try {
            File localConfigFile = ContextUtils.getLocalConfigFile(resources);
            if (localConfigFile != null) {
                return parse(tClass, new FileInputStream(localConfigFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parse(tClass, resources);
    }
}
