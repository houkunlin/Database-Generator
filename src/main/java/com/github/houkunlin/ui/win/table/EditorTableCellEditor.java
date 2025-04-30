package com.github.houkunlin.ui.win.table;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaCodeFragment;
import com.intellij.psi.JavaCodeFragmentFactory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.ui.EditorTextField;
import com.intellij.util.ui.AbstractTableCellEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

/**
 * 支持自动完成包名的表格编辑器
 *
 * @author daiwenzh5
 * @since 2.8.4
 */
public class EditorTableCellEditor extends AbstractTableCellEditor {

    private final EditorTextField editorTextField;

    /**
     * 获取焦点时的单元格值
     */
    private String cellValue;

    public EditorTableCellEditor(Project project) {
        this.editorTextField = createEditorTextField(project);
        // 当获取到焦点时，尝试将输入框的值设置为单元格值
        this.editorTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (editorTextField.getText()
                                   .equals(cellValue)) {
                    return;
                }
                editorTextField.setText(Objects.toString(cellValue, ""));
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return editorTextField.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // 记录单元格的值
        this.cellValue = value.toString();
        return editorTextField;
    }

    private EditorTextField createEditorTextField(Project project) {
        // https://jetbrains.org/intellij/sdk/docs/user_interface_components/editor_components.html
        JavaCodeFragment code = JavaCodeFragmentFactory.getInstance(project)
                                                       .createReferenceCodeFragment("", null, true, false);
        Document document = PsiDocumentManager.getInstance(project)
                                              .getDocument(code);
        JavaFileType fileType = JavaFileType.INSTANCE;
        return new EditorTextField(document, project, fileType);
    }
}
