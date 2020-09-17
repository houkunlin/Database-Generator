package com.github.houkunlin.util;

import com.intellij.codeInsight.actions.AbstractLayoutCodeProcessor;
import com.intellij.codeInsight.actions.OptimizeImportsProcessor;
import com.intellij.codeInsight.actions.RearrangeCodeProcessor;
import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.ExceptionUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 文件处理工具
 *
 * @author HouKunLin
 * @date 2020 /9/17 0017 11:00
 * @see <a href="https://github.com/makejavas/EasyCode/blob/8da33606c991e54a0c59423f33d4cd32052100bd/src/main/java/com/sjhy/plugin/tool/FileUtils.java">参考资料</a>
 */
public class FileUtils {
    private static final Logger LOG = Logger.getInstance(FileUtils.class);
    private static volatile FileUtils fileUtils;

    private FileUtils() {
    }

    /**
     * 单例模式
     *
     * @return the instance
     */
    public static FileUtils getInstance() {
        if (fileUtils == null) {
            synchronized (FileUtils.class) {
                if (fileUtils == null) {
                    fileUtils = new FileUtils();
                }
            }
        }
        return fileUtils;
    }

    /**
     * 获得一个文件的目录，如果不存在则创建该目录
     *
     * @param project  项目对象
     * @param saveFile 文件对象
     * @return 该文件对象的目录
     */
    public PsiDirectory getPsiDirectory(Project project, File saveFile) {
        String filePath = saveFile.getParent();
        // PSI对象管理器
        PsiManager psiManager = PsiManager.getInstance(project);
        VirtualFile directory = LocalFileSystem.getInstance().findFileByPath(filePath);
        if (directory == null) {
            return WriteCommandAction.runWriteCommandAction(project, (Computable<PsiDirectory>) () -> {
                try {
                    VirtualFile dir = VfsUtil.createDirectoryIfMissing(filePath);
                    LOG.assertTrue(dir != null);
                    return psiManager.findDirectory(dir);
                } catch (IOException e) {
                    LOG.error("path {} error", filePath);
                    ExceptionUtil.rethrow(e);
                    return null;
                }
            });
        }
        return psiManager.findDirectory(directory);
    }

    /**
     * 写入文件
     *
     * @param project      项目对象
     * @param saveFile     需要保存的文件对象
     * @param content      文件的保存内容
     * @param overrideFile 如果已经存在该文件，是否覆盖文件内容
     * @return 返回这个新保存的文件对象
     */
    public PsiFile saveFileContent(Project project, File saveFile, String content, boolean overrideFile) {
        return WriteCommandAction.runWriteCommandAction(project, (Computable<PsiFile>) () -> {
            // 保存文件的路径、目录对象
            PsiDirectory psiDirectory = getPsiDirectory(project, saveFile);
            if (psiDirectory == null) {
                return null;
            }
            String fileName = saveFile.getName();

            // 保存或替换文件
            PsiFile oldFile = psiDirectory.findFile(fileName);
            if (oldFile == null) {
                PsiFile psiFile = PsiFileFactory.getInstance(project).createFileFromText(fileName, FileTypes.UNKNOWN, content);
                return (PsiFile) psiDirectory.add(psiFile);
            } else {
                if (overrideFile) {
                    // 文档管理器
                    PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
                    Document document = psiDocumentManager.getDocument(oldFile);
                    LOG.assertTrue(document != null);
                    // 对旧文件进行替换操作
                    document.setText(content);
                    // 提交所有改动，并非CVS中的提交文件
                    psiDocumentManager.commitAllDocuments();
                }
                return oldFile;
            }
        });
    }

    /**
     * 重新格式化代码
     *
     * @param project 项目对象
     * @param psiFile 文件对象
     * @see <a href="https://github.com/makejavas/EasyCode/blob/8da33606c991e54a0c59423f33d4cd32052100bd/src/main/java/com/sjhy/plugin/tool/FileUtils.java#L143">参考资料</a>
     */
    @SuppressWarnings("unchecked")
    public void reformatCode(Project project, PsiFile[] psiFile) {
        // 尝试对文件进行格式化处理
        AbstractLayoutCodeProcessor processor = new ReformatCodeProcessor(project, psiFile, null, false);
        // 优化导入
        processor = new OptimizeImportsProcessor(processor);
        // 重新编排代码（会将代码中的属性与方法的顺序进行重新调整）
        processor = new RearrangeCodeProcessor(processor);

        // 清理代码，进行旧版本兼容，旧版本的IDEA尚未提供该处理器
        try {
            Class<AbstractLayoutCodeProcessor> codeCleanupCodeProcessorCls = (Class<AbstractLayoutCodeProcessor>) Class.forName("com.intellij.codeInsight.actions.CodeCleanupCodeProcessor");
            Constructor<AbstractLayoutCodeProcessor> constructor = codeCleanupCodeProcessorCls.getConstructor(AbstractLayoutCodeProcessor.class);
            processor = constructor.newInstance(processor);
        } catch (ClassNotFoundException ignored) {
            // 类不存在直接忽略
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            // 抛出未知异常
            ExceptionUtil.rethrow(e);
        }
        // 执行处理
        processor.run();
    }
}
