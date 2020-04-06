package com.github.houkunlin.freemarker;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("all")
public class TemplateAction implements TemplateDirectiveModel {
    private final Consumer<String> filenameConsumer;
    private final Consumer<String> filepathConsumer;
    private final Consumer<String> typeConsumer;

    public TemplateAction(Consumer<String> filenameConsumer, Consumer<String> filepathConsumer, Consumer<String> typeConsumer) {
        this.filenameConsumer = filenameConsumer;
        this.filepathConsumer = filepathConsumer;
        this.typeConsumer = typeConsumer;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        for (Object object : params.entrySet()) {
            Map.Entry ent = (Map.Entry) object;

            String paramName = String.valueOf(ent.getKey());
            TemplateModel paramValue = (TemplateModel) ent.getValue();
            System.out.println("模板变量信息：paramName: " + paramName + ", paramValue" + paramValue);
            String string;
            switch (paramName) {
                /**
                 * 文件名称
                 */
                case "filename":
                    if (!(paramValue instanceof SimpleScalar)) {
                        throw new TemplateModelException("The \"" + paramName + "\" parameter must be a String.");
                    }
                    string = ((SimpleScalar) paramValue).getAsString();
                    filenameConsumer.accept(string);
                    break;
                /**
                 * 文件保存地址
                 */
                case "filepath":
                    if (!(paramValue instanceof SimpleScalar)) {
                        throw new TemplateModelException("The \"" + paramName + "\" parameter must be a String.");
                    }
                    string = ((SimpleScalar) paramValue).getAsString();
                    filepathConsumer.accept(string);
                    break;
                /**
                 * 文件类型：实体类、DAO、Service、Controller
                 */
                case "type":
                    if (!(paramValue instanceof SimpleScalar)) {
                        throw new TemplateModelException("The \"" + paramName + "\" parameter must be a String.");
                    }
                    string = ((SimpleScalar) paramValue).getAsString();
                    typeConsumer.accept(string);
                    break;
                default:
                    throw new TemplateModelException("Unsupported parameter: " + paramName);
            }
        }

        if (body != null) {
            // Executes the nested body (same as <#nested> in FTL). In this
            // case we don't provide a special writer as the parameter:
            body.render(env.getOut());
        }
    }

}
