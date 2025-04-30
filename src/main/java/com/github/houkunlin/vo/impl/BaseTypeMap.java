package com.github.houkunlin.vo.impl;

import com.github.houkunlin.config.Settings;
import com.github.houkunlin.model.FileType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 一个基础的类型映射，子类必须显示调用{@link #init(Settings, Function)} 进行初始化。
 *
 * @author daiwenzh5
 */
public abstract class BaseTypeMap<T> {

    private final Map<String, T> map = new HashMap<>();

    private boolean initialized = false;

    /**
     * 根据类型获取值
     *
     * @param type 类型
     * @return 映射值
     */
    public final T get(String type) {
        if (!initialized) {
            throw new IllegalStateException("请先调用 init() 方法初始化");
        }
        return map.get(type);
    }

    /**
     * 初始化类型映射
     *
     * @param settings 设置信息
     * @param mapping  映射器
     */
    protected void init(Settings settings, Function<FileType, T> mapping) {
        map.clear();
        settings.getFileTypes()
                .forEach(item -> map.put(item.getType(), mapping.apply(item)));
        this.initialized = true;
    }


}
