# Database Generator

> 一个依赖 IDEA DatabaseTools 的代码生成器，通过数据库表结构生成相应的Java代码，插件提供一套简单的增删查改代码模板，也可以新增自定义模板来生成前端代码或其他相关的代码。



插件初次运行时会在当前目录下创建 `generator` 目录用来存放插件所需要的信息，其中 `generator/templates` 目录中存放了所需要生成的代码模板文件。

本插件支持 `beetl`/`freemarker`/`velocity` 三种模板引擎的代码模板，通过文件后缀(`btl`/`ftl`/`vm`)来选择使用什么模板引擎渲染，自定义模板请阅读 [模板变量文档](./doc/template-document.md) 了解相关变量内容后再进行自定义开发。



[更新日志](./doc/changeNotes.md) | [模板变量说明](./doc/template-document.md) | [旧版模板升级2.0.0插件版本指南](./doc/upgrade-2.0.0.md) | [插件截图](./doc/images.md) 



## 操作录制

插件用法

![插件使用方式](./doc/assets/use_1.gif)



默认提供三种模板引擎（`beetl`/`freemarker`/`velocity`）的代码模板（SpringBoot+MyBatis-Plus+自定义工具），可选择保留其中一种模板引擎的代码模板，然后根据自己的需求对代码模板进行修改，阅读 [模板变量说明](./doc/template-document.md) 了解模板变量的具体内容。

![自定义代码模板](./doc/assets/use_2.gif)





## 参考代码
- better-mybatis-generator https://github.com/kmaster/better-mybatis-generator


