package com.github.houkunlin.template.beetl;

import org.beetl.core.ConsoleErrorHandler;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.io.NoLockStringWriter;

import java.io.IOException;
import java.io.Writer;

/**
 * Beetl 模板的异常处理器
 *
 * @author HouKunLin
 * @date 2020/7/18 0018 1:51
 */
public class BeetlErrorHandler extends ConsoleErrorHandler {
    @Override
    public void processExcption(BeetlException ex, Writer writer) {
        Writer w = new NoLockStringWriter();
        super.processExcption(ex, w);
        throw new RuntimeException(w.toString(), ex);
    }

    @Override
    protected void println(Writer w, String msg) {
        append(w, msg, "\n");
        super.println(w, msg);
    }

    @Override
    protected void print(Writer w, String msg) {
        append(w, msg);
        super.print(w, msg);
    }

    private void append(Writer w, String... strings) {
        try {
            for (String string : strings) {
                w.append(string);
            }
        } catch (IOException ignored) {
        }
    }
}
