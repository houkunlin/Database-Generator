package com.github.houkunlin.vo;

/**
 * 模板传递给程序的变量信息
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 0:58
 */
public class Variable {
    public static String filename;
    public static String filepath;
    public static String type;

    private static Variable instance;

    private Variable() {
    }

    public synchronized static Variable getInstance() {
        if (instance == null) {
            instance = new Variable();
        }
        resetVariables();
        return instance;
    }

    public static void resetVariables() {
        filename = null;
        filepath = null;
        type = null;
    }

    public static String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        Variable.filename = filename;
    }

    public static String getFilepath() {
        return filepath;
    }

    public static void setFilepath(String filepath) {
        Variable.filepath = filepath;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Variable.type = type;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "filename='" + filename + '\'' +
                ", filepath='" + filepath + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
