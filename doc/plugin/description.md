[Github](https://github.com/houkunlin/Database-Generator) | [Gitee](https://gitee.com/houkunlin/Database-Generator) | [Document](https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md) 

**English (Google Translate)** / 中文

<br>

**2.0.0 is not compatible with 1.x version, please check the [Code Template Upgrade Guide](https://github.com/houkunlin/Database-Generator/blob/master/doc/upgrade-2.0.0.md), and check the detailed writing [Template Variable Document](https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md)**

<br>

A code generator that relies on the IDEA Database tool to generate add, delete, check, modify, and function codes simply through the database table structure.

You can use custom templates to generate the required code information.

When the plugin is run for the first time, a generator directory will be created in the current directory to store the information required by the plugin. The generator/templates directory contains the code template files that need to be generated.

This plugin supports `beetl`/`freemarker`/`velocity` code templates of three template engines. You can choose which template engine to use for the file suffix (`btl`/`ftl`/`vm`). For custom templates, please Read [Template Variable Document](https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md) to understand the contents of related variables before custom development.

<br><br>

**中文** / English

<br>

**2.0.0 与 1.x 版本不兼容，请查看 [代码模板升级指南](https://github.com/houkunlin/Database-Generator/blob/master/doc/upgrade-2.0.0.md) ，和查看详细的编写 [模板变量文档](https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md)**

<br>

一个依赖 IDEA Database 工具的代码生成器，通过数据库表结构简单的生成增、删、查、改、功能代码。

可通过自定义模板来生成所需要的代码信息。

插件初次运行时会在当前目录下创建 generator 目录用来存放插件所需要的信息，其中 `generator/templates` 目录中存放了所需要生成的代码模板文件。

本插件支持 `beetl`/`freemarker`/`velocity` 三种模板引擎的代码模板，通过文件后缀(`btl`/`ftl`/`vm`)来选择使用什么模板引擎渲染，自定义模板请阅读 [模板变量文档](https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md) 了解相关变量内容后再进行自定义开发。

<br><br>
Author: HouKunLin<br>
Email: 1184511588@qq.com

