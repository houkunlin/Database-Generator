[Github](https://github.com/houkunlin/Database-Generator) | [Gitee](https://gitee.com/houkunlin/Database-Generator) | <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md">Document</a> 

**English (Google Translate)** / 中文

<br>

**2.x is not compatible with 1.x version, please check the <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/upgrade-2.0.0.md">Code Template Upgrade Guide</a>, and check the detailed writing <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md">Template Variable Document</a>**

<br>

A code generator that relies on the IDEA Database tool to generate the corresponding Java code through the database table structure. The plug-in provides a simple set of add, delete, and modify code templates. You can also add custom templates to generate front-end code or other related code.

When the plugin is run for the first time, a `generator` directory will be created in the current directory to store the information required by the plugin. The `generator/templates` directory contains the code template files that need to be generated.

This plugin supports `beetl`/`freemarker`/`velocity` code templates of three template engines. You can choose which template engine to use for the file suffix (`btl`/`ftl`/`vm`). For custom templates, please Read <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md">Template Variable Document</a> to understand the contents of related variables before custom development.

<br><br>

**中文** / English

<br>

**2.x 与 1.x 版本不兼容，请查看 <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/upgrade-2.0.0.md">代码模板升级指南</a> ，和查看详细的编写 <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md">模板变量文档</a>**

<br>

一个依赖 IDEA Database 工具的代码生成器，通过数据库表结构生成相应的Java代码，插件提供一套简单的增删查改代码模板，也可以新增自定义模板来生成前端代码或其他相关的代码。

插件初次运行时会在当前目录下创建 `generator` 目录用来存放插件所需要的信息，其中 `generator/templates` 目录中存放了所需要生成的代码模板文件。

本插件支持 `beetl`/`freemarker`/`velocity` 三种模板引擎的代码模板，通过文件后缀(`btl`/`ftl`/`vm`)来选择使用什么模板引擎渲染，自定义模板请阅读 <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md">模板变量文档</a> 了解相关变量内容后再进行自定义开发。

<br><br>
Author: HouKunLin<br>
Email: 1184511588@qq.com

