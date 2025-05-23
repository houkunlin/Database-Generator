package com.github.houkunlin.template.beetl;

import org.beetl.core.ConsoleErrorHandler;
import org.beetl.core.GroupTemplate;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.io.NoLockStringWriter;

import java.io.IOException;
import java.io.Writer;

/**
 * Beetl 模板的异常处理器
 *
 * @author HouKunLin
 */
public class BeetlErrorHandler extends ConsoleErrorHandler {
    @Override
    public void processException(BeetlException ex, GroupTemplate groupTemplate, Writer writer) {
        Writer w = new NoLockStringWriter();
        super.processException(ex, groupTemplate, w);
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
