# Database Generator

一个代码生成器，通过数据库表结构简单的生成增删查改代码。

可通过自定义模板来生成所需要的代码信息。

插件初次运行时会在当前目录下创建 generator 目录用来存放插件所需要的信息，其中 generator/templates 目录中存放了所需要生成的代码模板文件，模板采用 freemarker 来渲染，因此在模板中可以使用 freemarker 的一切特性。

模板自定义了 `<@gen />` 指令，可接受3个参数`type`/`filename`/`filepath`

- `type` : `entity` / `dao` / `service` / `serviceImpl` / `controller` / `xml` / `other`
- `filename` ：会覆盖默认的文件名配置：`EntityName + Suffix + '.java'`
- `filepath` : 会覆盖默认的文件路径配置：`javaPath/sourcePath + package`

一般情况下使用 <@gen type="entity" /> 来标记当前文件的类型，然后根据文件类型来使用一些默认的配置（文件名、包名、保存路径），
        如果不适用默认的配置（文件名、包名、保存路径），则可以使用 <@gen filename="${table.entityName}DTO.java" filepath="src/main/java/com.example.domain.dto" /> 来覆盖默认配置

## 参考代码
- better-mybatis-generator https://github.com/kmaster/better-mybatis-generator