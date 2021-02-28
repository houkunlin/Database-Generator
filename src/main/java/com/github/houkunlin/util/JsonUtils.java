package com.github.houkunlin.util;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Json 工具
 *
 * @author HouKunLin
 */
public class JsonUtils {
    private static final Gson GSON = new Gson();

    private JsonUtils() {
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
        return GSON.fromJson(jsonString, tClass);
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
        return GSON.fromJson(jsonString, tClass);
    }

    /**
     * 从JSON内容中解析字段类型映射关系对象
     *
     * @param tClass    转换类型
     * @param resources 资源名称
     * @param <T>       类型对象
     * @return 类型对象
     */
    public static <T> T parseJson(Class<T> tClass, String resources) {
        try {
            File jsonFile = PluginUtils.getExtensionPluginDirFile(resources);
            if (jsonFile.exists()) {
                return parse(tClass, new FileInputStream(jsonFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parse(tClass, resources);
    }
}
