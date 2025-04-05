package com.github.houkunlin.util;

import com.intellij.openapi.diagnostic.Logger;
import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.reflection.CachedMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
    private final static Logger logger = Logger.getInstance(ScriptManager.class);
    /**
     * 忽略一些内置的默认方法
     */
    private final static String[] IGNORED_METHOD_NAMES = {"equals", "getClass", "hashCode", "notify", "notifyAll", "toString", "wait", "getMetaClass", "evaluate", "getBinding", "getProperty", "invokeMethod", "print", "printf", "println", "run", "setBinding", "setProperty"};

    /**
     * 缓存,确保相同的脚本目录,只加载一次
     */
    private final static WeakHashMap<String, ScriptManager> cache = new WeakHashMap<>();
    private static final ScriptManager EMPTY = new ScriptManager(null);
    private final Path scriptDir;
    private final GroovyScriptEngine gse;
    private final Map<String, AnyCallback> methods = new HashMap<>();

    private ScriptManager(Path scriptDir) {
        if (scriptDir == null) {
            this.scriptDir = null;
            this.gse = null;
            return;
        }
        if (!Files.exists(scriptDir)) {
            logger.warn("脚本: " + scriptDir + "不存在");
            this.scriptDir = null;
            this.gse = null;
            return;
        }
        this.scriptDir = scriptDir;
        gse = initGroovyScriptEngine(scriptDir);
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
        ScriptManager scriptManager = cache.computeIfAbsent(scriptDir.toString(), key -> new ScriptManager(scriptDir));
        if (reload) {
            scriptManager.scan();
        }
        return scriptManager;
    }

    private static boolean noneMatchIgnoredMethodName(CachedMethod method) {
        return Arrays.stream(IGNORED_METHOD_NAMES)
                     .noneMatch(it -> it.equals(method.getName()));
    }

    private GroovyScriptEngine initGroovyScriptEngine(Path scriptDir) {
        try {
            return new GroovyScriptEngine(scriptDir.toString());
        } catch (IOException e) {
            return null;
        }
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

    private void registerScript(Path scriptPath) {
        try {
            // 编译脚本并提取方法
            Script script = gse.createScript(scriptPath.getFileName()
                                                       .toString(), new Binding());
            script.getMetaClass()
                  .getMethods()
                  .stream()
                  .filter(method -> method instanceof CachedMethod)
                  .map(method -> (CachedMethod) method)
                  .filter(method -> !method.isStatic() && !method.isPrivate())
                  .filter(ScriptManager::noneMatchIgnoredMethodName)
                  .forEach(method -> {
                      String methodName = method.getName();
                      methods.put(methodName, args -> script.invokeMethod(methodName, args));
                  });
        } catch (Exception e) {
            throw new RuntimeException("Failed to load script: " + scriptPath, e);
        }
    }

    /**
     * 获取方法
     *
     * @param name 方法名
     * @return 一个方法
     */
    public AnyCallback get(String name) {
        return methods.get(name);
    }

    /**
     * 遍历所有方法
     *
     * @param action 操作
     */
    public void forEach(BiConsumer<String, AnyCallback> action) {
        methods.forEach(action);
    }

}
