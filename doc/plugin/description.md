[Github](https://github.com/houkunlin/Database-Generator) | [Gitee](https://gitee.com/houkunlin/Database-Generator)

**English (Google Translate)** / 中文

A code generator that relies on the IDEA Database tool to generate add, delete, check, modify, and function codes simply through the database table structure.

You can use custom templates to generate the required code information.

When the plugin is run for the first time, the generator directory will be created in the current directory to store the information required by the plugin. The generator / templates directory contains the code template files that need to be generated.          The template is rendered with freemarker, so all the features of freemarker can be used in the template.

The template customizes the <@gen /> instruction, which can accept 3 parameters type/filename/filepath。

- `type` : entity / dao / service / serviceImpl / controller / xml / other
- `filename` ：Will override the default file name configuration: EntityName + Suffix + '.java'
- `filepath` : Will override the default file path configuration: javaPath/sourcePath + package

In general, use `<@gen type="entity" />` to mark the type of the current file, Then use some default configuration (file name, package name, save path) according to the file type, If the default configuration (file name, package name, save path) is not applicable, you can use `<@gen filename="${'$'}{table.entityName}DTO.java" filepath="src/main/java/com.example.domain.dto" />` to override the default configuration

**中文** / English

一个依赖 IDEA Database 工具的代码生成器，通过数据库表结构简单的生成增、删、查、改、功能代码。

可通过自定义模板来生成所需要的代码信息。

插件初次运行时会在当前目录下创建 generator 目录用来存放插件所需要的信息，其中 generator/templates 目录中存放了所需要生成的代码模板文件， 模板采用 freemarker 来渲染，因此在模板中可以使用 freemarker 的一切特性。

模板自定义了 <@gen /> 指令，可接受3个参数 type/filename/filepath。

- `type` : entity / dao / service / serviceImpl / controller / xml / other
- `filename` ：会覆盖默认的文件名配置：EntityName + Suffix + '.java'
- `filepath` : 会覆盖默认的文件路径配置：javaPath/sourcePath + package

一般情况下使用 `<@gen type="entity" />` 来标记当前文件的类型，然后根据文件类型来使用一些默认的配置（文件名、包名、保存路径）， 如果不适用默认的配置（文件名、包名、保存路径），则可以使用 `<@gen filename="${'$'}{table.entityName}DTO.java" filepath="src/main/java/com.example.domain.dto" />` 来覆盖默认配置

Author：HouKunLin
<br>
Email：1184511588@qq.com