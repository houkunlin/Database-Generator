package com.github.houkunlin.util;

import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * yaml工具
 * @author daiwenzh5
 * @since 2.8.4
 */
@UtilityClass
public class YamlUtils {

    private final static Map<String, Yaml> cache = new ConcurrentHashMap<>();

    /**
     * 从输入流中读取yaml文件，并转换为指定对象
     *
     * @param input 输入流
     * @param clazz 类
     * @param <T>   对象类型
     * @return 对象
     */
    public <T> Optional<T> load(InputStream input, Class<T> clazz) {
        return load(clazz, yaml -> yaml.loadAs(input, clazz));
    }

    /**
     * 从阅读字符流中读取yaml文件，并转换为指定对象
     *
     * @param input 阅读字符流
     * @param clazz 类
     * @param <T>   对象类型
     * @return 对象
     */
    public <T> Optional<T> load(Reader input, Class<T> clazz) {
        return load(clazz, yaml -> yaml.loadAs(input, clazz));
    }

    /**
     * 从字符串中读取yaml文件，并转换为指定对象
     *
     * @param input 输入字符串
     * @param clazz 类
     * @param <T>   对象类型
     * @return 对象
     */
    public <T> Optional<T> load(String input, Class<T> clazz) {
        return load(clazz, yaml -> yaml.loadAs(input, clazz));
    }

    private <T> Optional<T> load(Class<T> clazz, Function<Yaml, T> loader) {
        try {
            var yaml = cache.computeIfAbsent(clazz.getName(), k -> buildYamlInstance(clazz));
            return Optional.ofNullable(loader.apply(yaml));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Yaml buildYamlInstance(Class<?> clazz) {
        var constructor = new Constructor(clazz, new LoaderOptions());
        constructor.setPropertyUtils(new PropertyUtils() {
            @Override
            public Property getProperty(Class<?> type, String name) {
                if (name.indexOf('-') > -1) {
                    name = camelize(name);
                }
                // 关键代码 忽略yaml中无法在类中找到属性的字段
                setSkipMissingProperties(true);
                return super.getProperty(type, name);
            }
        });
        return new Yaml(constructor);
    }

    private static String camelize(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-') {
                input = input.substring(0, i) + input.substring(i + 1, i + 2)
                                                     .toUpperCase() + input.substring(i + 2);
            }
            if (input.charAt(i) == ' ') {
                input = input.substring(0, i) + input.substring(i + 1, i + 2)
                                                     .toUpperCase() + input.substring(i + 2);
            }
        }
        return input;
    }
}
