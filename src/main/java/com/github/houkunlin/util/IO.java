package com.github.houkunlin.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class IO {
    /**
     * 获取资源文件的输入流
     *
     * @param resources 资源文件
     * @return 文件输入流
     */
    public static InputStream getInputStream(String resources) {
        return IO.class.getClassLoader().getResourceAsStream(resources);
    }

    /**
     * 读取插件内部 Classpath 资源文件内容信息
     *
     * @param resources 资源路径
     * @return 文件内容
     */
    public static String readResources(String resources) {
        return read(getInputStream(resources));
    }

    /**
     * 从输入流中读取字符串内容
     *
     * @param inputStream 输入流
     * @return 字符串内容
     */
    public static String read(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }
        byte[] bytes = new byte[1024];
        int len;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(outputStream, inputStream);
        }
        return "";
    }

    /**
     * 关闭输入、输出流
     *
     * @param closeables 可关闭流对象
     */
    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
