package com.github.houkunlin.util;

import com.intellij.openapi.ui.Messages;

import java.io.*;

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

    public static String readResources(String resources) {
        return read(getInputStream(resources));
    }

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
            return new String(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(outputStream, inputStream);
        }
        return "";
    }

    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 保存内容到文件
     *
     * @param file   保存的文件
     * @param result 文件内容信息
     * @throws IOException 异常
     */
    public static void writeToFile(File file, String result) throws IOException {
        writeToFile(file, new ByteArrayInputStream(result.getBytes()));
    }

    /**
     * 保存内容到文件
     *
     * @param file        保存的文件
     * @param inputStream 文件输入流
     * @throws IOException 异常
     */
    public static void writeToFile(File file, InputStream inputStream) throws IOException {
        if (file == null) {
            return;
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                Messages.showMessageDialog("在保存 \"" + file + "\"文件时，创建\"" + parentFile + "\"路径失败", "创建路径失败", Messages.getErrorIcon());
            }
        }
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buf = new byte[1024];

        while (true) {
            int read = inputStream.read(buf, 0, buf.length);
            if (read == -1) {
                break;
            }
            outputStream.write(buf, 0, read);
        }
        outputStream.flush();
        outputStream.close();
    }
}
