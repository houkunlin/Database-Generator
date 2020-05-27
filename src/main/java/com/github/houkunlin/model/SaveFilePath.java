package com.github.houkunlin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 保存文件信息
 *
 * @author HouKunLin
 * @date 2020/4/3 0003 20:52
 */
public class SaveFilePath {
    /**
     * 模板 gen 指令允许的 type 值类型
     */
    private static final List<String> types = new ArrayList<>();

    static {
        types.add("entity");
        types.add("dao");
        types.add("service");
        types.add("serviceImpl");
        types.add("controller");
        types.add("xml");
    }

    /**
     * 模板文件的 type 类型
     */
    private String type;
    /**
     * 模板文件保存路径
     */
    private String toString;

    public SaveFilePath(AtomicReference<String> filename, AtomicReference<String> filepath, String defaultFilename, String defaultFilepath) {
        String name = getValue(filename, defaultFilename);
        String path = getValue(filepath, defaultFilepath);
        toString = (path.replace(".", "/") + "/" + name);
        toString = toString.replace("\\", "/").replaceAll("/+", "/");
    }

    private String getValue(AtomicReference<String> atomicReference, String defaultValue) {
        String tempValue = atomicReference.get();
        if (tempValue != null) {
            return tempValue;
        } else {
            return defaultValue;
        }
    }

    public static SaveFilePath create(AtomicReference<String> filename, AtomicReference<String> filepath, AtomicReference<String> type, Table table, Settings settings) {
        SaveFilePath saveFilePath;
        String s = type.get();
        if (s == null) {
            return new SaveFilePath(filename, filepath,
                    table.getEntityName() + ".java",
                    settings.getSourcesPathAt("temp"));
        }
        switch (s) {
            case "entity":
                saveFilePath = new SaveFilePath(filename, filepath,
                        table.getEntityName() + settings.getEntitySuffix() + ".java",
                        settings.getJavaPathAt(settings.getEntityPackage()));
                break;
            case "dao":
                saveFilePath = new SaveFilePath(filename, filepath,
                        table.getEntityName() + settings.getDaoSuffix() + ".java",
                        settings.getJavaPathAt(settings.getDaoPackage()));
                break;
            case "service":
                saveFilePath = new SaveFilePath(filename, filepath,
                        table.getEntityName() + settings.getServiceSuffix() + ".java",
                        settings.getJavaPathAt(settings.getServicePackage()));
                break;
            case "serviceImpl":
                saveFilePath = new SaveFilePath(filename, filepath,
                        table.getEntityName() + settings.getServiceSuffix() + "Impl.java",
                        settings.getJavaPathAt(settings.getServicePackage() + ".impl"));
                break;
            case "controller":
                saveFilePath = new SaveFilePath(filename, filepath,
                        table.getEntityName() + settings.getControllerSuffix() + ".java",
                        settings.getJavaPathAt(settings.getControllerPackage()));
                break;
            case "xml":
                saveFilePath = new SaveFilePath(filename, filepath,
                        table.getEntityName() + settings.getDaoSuffix() + ".xml",
                        settings.getSourcesPathAt(settings.getXmlPackage()));
                break;
            default:
                saveFilePath = new SaveFilePath(filename, filepath,
                        table.getEntityName() + ".java",
                        settings.getSourcesPathAt("temp"));
        }
        saveFilePath.type = type.get();
        return saveFilePath;
    }

    /**
     * 判断是否是某种类型的文件
     *
     * @param type 文件类型
     * @return 结果
     */
    public boolean isType(String type) {
        if (this.type == null) {
            return false;
        }
        return this.type.equals(type);
    }

    public boolean isEntity() {
        return "entity".equals(type);
    }

    public boolean isDao() {
        return "dao".equals(type);
    }

    public boolean isService() {
        return "service".equals(type);
    }

    public boolean isServiceImpl() {
        return "serviceImpl".equals(type);
    }

    public boolean isController() {
        return "controller".equals(type);
    }

    public boolean isJava() {
        return types.contains(type) && !isXml();
    }

    public boolean isXml() {
        return "xml".equals(type);
    }

    public boolean isOther() {
        return !types.contains(type);
    }

    @Override
    public String toString() {
        return toString;
    }
}
