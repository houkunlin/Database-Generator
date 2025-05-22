![Java CI with Gradle](https://github.com/houkunlin/Database-Generator/workflows/Java%20CI%20with%20Gradle/badge.svg) 

# Database Generator

> 一个依赖 IDEA DatabaseTools 的代码生成器，通过数据库表结构生成相应的Java代码，插件提供一套简单的增删查改代码模板，也可以新增自定义模板来生成前端代码或其他相关的代码。



插件初次运行时会在当前目录下创建 `generator` 目录用来存放插件所需要的信息，其中 `generator/templates` 目录中存放了所需要生成的代码模板文件。

本插件支持 `beetl`/`freemarker`/`velocity` 三种模板引擎的代码模板，通过文件后缀(`btl`/`ftl`/`vm`)来选择使用什么模板引擎渲染，自定义模板请阅读 [模板变量文档](./doc/template-document.md) 了解相关变量内容后再进行自定义开发。



[更新日志](./doc/changeNotes.md) | [模板变量说明](./doc/template-document.md) | [旧版模板升级2.0.0插件版本指南](./doc/upgrade-2.0.0.md) | [插件截图](./doc/images.md) | [所有变量使用 all-variable.ftl](https://github.com/houkunlin/Database-Generator/blob/master/src/main/resources/templates/all-variable.ftl) | [渲染输出结果示例 all-variable.md](doc/all-variable.md)



默认提供三种模板引擎（`beetl`/`freemarker`/`velocity`）的代码模板（SpringBoot+MyBatis-Plus+自定义工具），可选择保留其中一种模板引擎的代码模板，然后根据自己的需求对代码模板进行修改，阅读 [模板变量说明](./doc/template-document.md) 了解模板变量的具体内容。

支持在模板中使用自定义的Groovy脚本，需要在模板父级目录下创建 `scripts` 文件夹，然后放置 `*.groovy` 文件即可。阅读 [Groovy脚本使用文档](./doc/groovy-scripts.md) 了解如何使用 Groovy 脚本。
```text
${工作空间}
|-templates
|-scripts
|--*.groovy
```
## 代码模板文件在IDEA中存放的位置

- 代码模板文件默认放到：`Scratches and Consoles/Extensions` （中文：草稿文件和控制台/扩展/Database Generator）
- 同时支持以下模板路径：`${project.dir}/.idea/generator/templates` 和 `${project.dir}/generator/templates`



## 注意事项

**版本 `v2.7.0` 对配置文件产生了破坏性变更，由原来的 `config/*.json` JSON格式配置文件改为 `config.yml` YAML配置文件** 

**在旧版本升级后，原来的 `config/*.json` 配置文件将失效，请参照 `src/main/resources/config.yml` 文件重制你自定义的配置文件**

**也可以删除本地的 `init.properties` 文件然后重新生成配置文件，但请注意备份您的自定义模板文件**



## 截图

![](./doc/assets/images_1.png)

![](./doc/assets/images_8.png)

![](./doc/assets/images_9.png)

![](./doc/assets/images_10.png)

![](./doc/assets/images_11.png)





## 参考代码
- better-mybatis-generator https://github.com/kmaster/better-mybatis-generator
- EasyCode https://github.com/makejavas/EasyCode

