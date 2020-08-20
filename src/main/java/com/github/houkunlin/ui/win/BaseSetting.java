package com.github.houkunlin.ui.win;

import com.github.houkunlin.config.Developer;
import com.github.houkunlin.config.Options;
import com.github.houkunlin.config.Settings;
import com.github.houkunlin.util.ContextUtils;
import com.google.common.collect.Maps;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaCodeFragment;
import com.intellij.psi.JavaCodeFragmentFactory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 基础设置窗口
 *
 * @author HouKunLin
 * @date 2020/8/15 0015 16:00
 */
public class BaseSetting implements IWindows, DocumentListener, com.intellij.openapi.editor.event.DocumentListener, ItemListener {
    /**
     * 配置对象：设置信息
     */
    private final Settings settings;
    /**
     * 配置对象：开发者信息
     */
    private final Developer developer;
    /**
     * 配置对象：其他参数
     */
    private final Options options;
    /**
     * 存放 setValue 与 输入框 的关系
     */
    private final Map<Consumer<String>, JTextComponent> map1 = Maps.newHashMap();
    /**
     * 存放 setValue 与 输入框 的关系
     */
    private final Map<Consumer<String>, EditorTextField> map2 = Maps.newHashMap();
    /**
     * 输入框：Entity前缀
     */
    private JTextField entitySuffixField;
    /**
     * 输入框：Entity包名
     */
    private EditorTextField entityPackageField;
    /**
     * 按钮：Entity 包选择
     */
    private JButton selectEntityPackageButton;
    /**
     * 输入框：Dao前缀
     */
    private JTextField daoSuffixField;
    /**
     * 输入框：Dao包名
     */
    private EditorTextField daoPackageField;
    /**
     * 按钮：Dao 包选择
     */
    private JButton selectDaoPackageButton;
    /**
     * 输入框：XML包名
     */
    private JTextField xmlPackageField;
    /**
     * 输入框：Service前缀
     */
    private JTextField serviceSuffixField;
    /**
     * 输入框：Service包名
     */
    private EditorTextField servicePackageField;
    /**
     * 按钮：Service 包选择
     */
    private JButton selectServicePackageButton;
    /**
     * 输入框：Controller前缀
     */
    private JTextField controllerSuffixField;
    /**
     * 输入框：Controller包名
     */
    private EditorTextField controllerPackageField;
    /**
     * 按钮：Controller 包选择
     */
    private JButton selectControllerPackageButton;
    /**
     * 输入框：开发者姓名
     */
    private JTextField authorField;
    /**
     * 输入框：电子邮件
     */
    private JTextField emailField;
    /**
     * 面板：顶级页面面板
     */
    private JPanel content;
    private JCheckBox overrideJavaCheckBox;
    private JCheckBox overrideXmlCheckBox;
    private JCheckBox overrideOtherCheckBox;

    public BaseSetting(Settings settings, Developer developer, Options options) {
        this.settings = settings;
        this.developer = developer;
        this.options = options;
        initDeveloper();
        initSettings();
        configSelectPackage();

        authorField.getDocument().addDocumentListener(this);
        emailField.getDocument().addDocumentListener(this);
        entitySuffixField.getDocument().addDocumentListener(this);
        daoSuffixField.getDocument().addDocumentListener(this);
        serviceSuffixField.getDocument().addDocumentListener(this);
        controllerSuffixField.getDocument().addDocumentListener(this);
        xmlPackageField.getDocument().addDocumentListener(this);

        map1.put(developer::setAuthor, authorField);
        map1.put(developer::setEmail, emailField);
        map1.put(settings::setEntitySuffix, entitySuffixField);
        map1.put(settings::setDaoSuffix, daoSuffixField);
        map1.put(settings::setServiceSuffix, serviceSuffixField);
        map1.put(settings::setControllerSuffix, controllerSuffixField);
        map1.put(settings::setXmlPackage, xmlPackageField);

        entityPackageField.getDocument().addDocumentListener(this);
        daoPackageField.getDocument().addDocumentListener(this);
        servicePackageField.getDocument().addDocumentListener(this);
        controllerPackageField.getDocument().addDocumentListener(this);

        map2.put(settings::setEntityPackage, entityPackageField);
        map2.put(settings::setDaoPackage, daoPackageField);
        map2.put(settings::setServicePackage, servicePackageField);
        map2.put(settings::setControllerPackage, controllerPackageField);

        overrideJavaCheckBox.addItemListener(this);
        overrideXmlCheckBox.addItemListener(this);
        overrideOtherCheckBox.addItemListener(this);
    }

    private void createUIComponents() {
        Project project = ContextUtils.getProject();
        entityPackageField = createEditorTextField(project, "");
        daoPackageField = createEditorTextField(project, "");
        servicePackageField = createEditorTextField(project, "");
        controllerPackageField = createEditorTextField(project, "");
    }

    /**
     * 创建一个可以自动补全包名的输入框
     *
     * @param project 项目
     * @param text    默认文本
     * @return IDEA 输入框组件
     */
    private EditorTextField createEditorTextField(Project project, String text) {
        // https://jetbrains.org/intellij/sdk/docs/user_interface_components/editor_components.html
        JavaCodeFragment code = JavaCodeFragmentFactory.getInstance(project).createReferenceCodeFragment(text, null, true, false);
        Document document = PsiDocumentManager.getInstance(project).getDocument(code);
        JavaFileType fileType = JavaFileType.INSTANCE;
        return new EditorTextField(document, project, fileType);
    }

    /**
     * 配置选择包名的按钮事件
     */
    private void configSelectPackage() {
        selectEntityPackageButton.addActionListener(e -> {
            chooserPackage(entityPackageField.getText(), entityPackageField::setText);
        });
        selectDaoPackageButton.addActionListener(e -> {
            chooserPackage(daoPackageField.getText(), daoPackageField::setText);
        });
        selectServicePackageButton.addActionListener(e -> {
            chooserPackage(servicePackageField.getText(), servicePackageField::setText);
        });
        selectControllerPackageButton.addActionListener(e -> {
            chooserPackage(controllerPackageField.getText(), controllerPackageField::setText);
        });
    }

    /**
     * 选择包名
     *
     * @param defaultSelect 默认选中包名
     * @param consumer      完成事件
     */
    private void chooserPackage(String defaultSelect, Consumer<String> consumer) {
        PackageChooserDialog chooser = new PackageChooserDialog("请选择模块包", ContextUtils.getProject());
        chooser.selectPackage(defaultSelect);
        chooser.show();
        PsiPackage psiPackage = chooser.getSelectedPackage();
        if (psiPackage != null) {
            consumer.accept(psiPackage.getQualifiedName());
        }
        chooser.getDisposable().dispose();
    }

    /**
     * 初始化开发者信息的输入框内容
     */
    private void initDeveloper() {
        authorField.setText(developer.getAuthor());
        emailField.setText(developer.getEmail());
    }

    /**
     * 初始化设置信息的输入框内容
     */
    private void initSettings() {
        entitySuffixField.setText(settings.getEntitySuffix());
        daoSuffixField.setText(settings.getDaoSuffix());
        serviceSuffixField.setText(settings.getServiceSuffix());
        controllerSuffixField.setText(settings.getControllerSuffix());

        entityPackageField.setText(settings.getEntityPackage());
        daoPackageField.setText(settings.getDaoPackage());
        servicePackageField.setText(settings.getServicePackage());
        controllerPackageField.setText(settings.getControllerPackage());
        xmlPackageField.setText(settings.getXmlPackage());
    }

    @Override
    public JPanel getContent() {
        return content;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    /**
     * IDEA 输入框组件内容更改事件
     *
     * @param event 事件
     */
    @Override
    public void documentChanged(@NotNull com.intellij.openapi.editor.event.DocumentEvent event) {
        Document document = event.getDocument();
        Set<Map.Entry<Consumer<String>, EditorTextField>> entries = map2.entrySet();
        for (Map.Entry<Consumer<String>, EditorTextField> entry : entries) {
            if (TextFieldDocumentUtil.updateSettingValue(document, entry.getValue(), entry.getKey())) {
                break;
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object item = e.getItem();
        if (overrideJavaCheckBox == item) {
            options.setOverrideJava(overrideJavaCheckBox.isSelected());
        } else if (overrideXmlCheckBox == item) {
            options.setOverrideXml(overrideXmlCheckBox.isSelected());
        } else if (overrideOtherCheckBox == item) {
            options.setOverrideOther(overrideOtherCheckBox.isSelected());
        }
    }

    /**
     * swing 输入框组件内容更改事件
     *
     * @param e 事件
     */
    public void documentChanged(DocumentEvent e) {
        javax.swing.text.Document document = e.getDocument();
        Set<Map.Entry<Consumer<String>, JTextComponent>> entries = map1.entrySet();
        for (Map.Entry<Consumer<String>, JTextComponent> entry : entries) {
            if (TextFieldDocumentUtil.updateSettingValue(document, entry.getValue(), entry.getKey())) {
                break;
            }
        }
    }
}
