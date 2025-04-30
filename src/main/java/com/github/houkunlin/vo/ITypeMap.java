package com.github.houkunlin.vo;

import com.github.houkunlin.config.Settings;
import com.github.houkunlin.model.FileType;

import java.util.Map;

/**
 * 基于类型的映射接口
 *
 * @author daiwenzh5
 * @since 1.0
 */
public interface ITypeMap<T, V> {

    /**
     * 根据类型获取映射对象
     *
     * @param type 类型
     * @return 结果
     */
    T get(String type);

    /**
     * 根据设置信息初始化
     *
     * @param settings 设置信息
     */
    default void initMore(Settings settings, V value) {
        var map = getMap();
        map.clear();
        settings.getFileTypes()
                .forEach(item -> map.put(item.getType(), this.mapping(item, value)));
    }

    /**
     * 映射
     *
     * @param fileType 文件类型
     * @return 结果
     */
    T mapping(FileType fileType, V value);

    /**
     * 获取映射集
     *
     * @return 映射集
     */
    Map<String, T> getMap();
}
