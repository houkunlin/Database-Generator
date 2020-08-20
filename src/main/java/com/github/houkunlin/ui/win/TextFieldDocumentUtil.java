package com.github.houkunlin.ui.win;

import com.intellij.openapi.editor.Document;
import com.intellij.ui.EditorTextField;

import javax.swing.text.JTextComponent;
import java.util.function.Consumer;

/**
 * 输入框修改内容工具
 *
 * @author HouKunLin
 * @date 2020/8/20 0020 11:11
 */
public class TextFieldDocumentUtil {

    /**
     * 更新配置信息的值
     *
     * @param document  文档
     * @param component 输入框组件
     * @param setValue  设置配置信息的set方法
     * @return 是否成功
     */
    public static boolean updateSettingValue(Document document, EditorTextField component, Consumer<String> setValue) {
        if (document == component.getDocument()) {
            setValue.accept(component.getText());
            return true;
        }
        return false;
    }

    /**
     * 更新配置信息的值
     *
     * @param document  文档
     * @param component 输入框组件
     * @param setValue  设置配置信息的set方法
     * @return 是否成功
     */
    public static boolean updateSettingValue(javax.swing.text.Document document, JTextComponent component, Consumer<String> setValue) {
        if (document == component.getDocument()) {
            setValue.accept(component.getText());
            return true;
        }
        return false;
    }
}
