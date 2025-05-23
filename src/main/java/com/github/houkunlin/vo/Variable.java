package com.github.houkunlin.vo;

import lombok.Getter;

/**
 * 模板传递给程序的变量信息
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 0:58
 */
public class Variable {
    @Getter
    public static String filename;
    @Getter
    public static String filepath;
    @Getter
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

    public static void setFilename(String filename) {
        Variable.filename = filename;
    }

    public static void setFilepath(String filepath) {
        Variable.filepath = filepath;
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
