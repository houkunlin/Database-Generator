package com.github.houkunlin.util;

import com.intellij.openapi.diagnostic.Logger;
import groovy.lang.GroovyClassLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;

/**
 * 脚本管理器
 *
 * @author daiwenzh5
 * @since 1.0
 */
public class ScriptManager {

    private final static Logger logger = Logger.getInstance(ScriptManager.class);

    /**
     * 脚本目录
     */
    public final static String DIR = "scripts";

    /**
     * 变量名
     */
    public final static String VARIABLE = DIR;

    /**
     * 命名空间
     */
    public final static String NAMESPACE = VARIABLE + ".";


    /**
     * 缓存,确保相同的脚本目录,只加载一次
     */
    private final static WeakHashMap<String, ScriptManager> cache = new WeakHashMap<>();
    private final Map<String, Object> scripts = new HashMap<>();
    private static final ScriptManager EMPTY = new ScriptManager();
    private final Path scriptDir;
    private final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    private ScriptManager() {
        this.scriptDir = null;
    }

    private ScriptManager(Path scriptDir) {
        if (!Files.exists(scriptDir)) {
            logger.warn("脚本: " + scriptDir + "不存在");
            this.scriptDir = null;
            return;
        }
        this.scriptDir = scriptDir;
        scan();
    }


    /**
     * 根据脚本目录获取脚本管理器，如果目录不存在，则返回一个空的脚本管理器。对于相同目录，优先从内存在获取。
     *
     * @param scriptDir 脚本目录
     * @param reload    是否重新加载
     * @return 一个脚本管理器
     */
    public static ScriptManager of(Path scriptDir, boolean reload) {
        if (scriptDir == null) {
            return EMPTY;
        }
        var scriptManager = cache.computeIfAbsent(scriptDir.toString(), key -> new ScriptManager(scriptDir));
        if (reload) {
            scriptManager.scan();
        }
        return scriptManager;
    }


    private void scan() {
        if (scriptDir == null) {
            return;
        }
        // 扫描脚本目录，加载所有.groovy文件
        try (var files = Files.walk(scriptDir)) {
            files.filter(path -> path.toString()
                                     .endsWith(".groovy"))
                 .forEach(this::registerScript);
        } catch (IOException ignored) {

        }

    }

    private void registerScript(Path path) {
        try {
            var file = path.toFile();
            Class<?> parsedClass = groovyClassLoader.parseClass(file);
            var fileName = file.getName();
            var instance = parsedClass.getDeclaredConstructor()
                                         .newInstance();
            logger.info("load script: " + scriptDir.relativize(path));
            scripts.put(fileName.replace(".groovy", ""), instance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load script: " + path, e);
        }
    }

    public Object get(String fileName) {
        return scripts.get(fileName);
    }

    /**
     * 遍历所有方法
     *
     * @param action 操作
     */
    public void forEach(BiConsumer<String, Object> action) {
        scripts.forEach(action);
    }

}
