- **2023-08-15: 2.8.1**

    - 升级 beetl 到 3.15.8.RELEASE
    - 升级 hutool-core 到 5.8.21
    - 升级 snakeyaml 到 2.1
    - 重构构建脚本
    - 增加一个选项来配置数据库的字段风格

- **2022-09-09: 2.8.0**

    - 修改默认模板位置到default/路径下
    - 添加刷新配置按钮
    - 更新 ibeetl模板版本
    - 更新开发插件版本
    - 贡献者：[tanqi(gitee)](https://gitee.com/Tanqishare) [tanqi(github)](https://github.com/TanqiZhou)

- **2022-09-07: 2.7.0**

  - feat: 配置文件由 json 改为 yml （破坏性变更）
  - 版本 `v2.7.0` 对配置文件产生了破坏性变更，由原来的 `config/*.json` JSON格式配置文件改为 `config.yml` YAML配置文件
  - 在旧版本升级后，原来的 `config/*.json` 配置文件将失效，请参照 [src/main/resources/config.yml](https://github.com/houkunlin/Database-Generator/blob/master/src/main/resources/config.yml) 文件重制你自定义的配置文件
  - 也可以删除本地的 `init.properties` 文件然后重新生成配置文件，但**请注意备份您的自定义模板文件**
  
- **2022-08-26: 2.6.1**

  - fix: 代码模板文件默认路径被定制后无法正常使用 #1
  - fix: 修复一个在 212.3116.43 被视为弃用的调用方式
  - chore: 修改模板文件内容，删除几个模板文件
  - 增加一个 jdbc-typescript-type.ftl 模板文件

- **2021-05-19: 2.6.0**

  - **ZH-CN**
  - feat: 实体类字段视图对象、数据库表字段视图对象增加一个 IDEA 内置的 DataType 类型的 dataType 字段。可用此字段获取数据库字段的设置的数据长度，具体用法请见最新的 [all-variable.ftl](https://github.com/houkunlin/Database-Generator/blob/master/src/main/resources/templates/all-variable.ftl) 代码模板
  - refactor: 初始化数据库表 URL 输入框内容把下划线替换成减号

- **2020-09-18: 2.4.0**

    - **&gt;&gt; ZH-CN**
    - feat: 增加LOGO图标
    - feat: 生成代码后代码格式化
    - feat: 生成代码过程中进度条展示
    - feat: 新增 **`date`** 时间信息模板变量，详情请查看  **[模板变量文档](https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md)**
    - **&gt;&gt; EN**
    - feat: Add LOGO icon
    - feat: code formatting after generating code
    - feat: progress bar display during code generation
    - feat: Added **`date`** time information template variable, please check for details **[Template Variable Document](https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md)**
    
- **2020-07-18: 2.0.0**
  
    - **&gt;&gt; ZH-CN**
    - **该版本与旧版本不兼容，请查看 <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/upgrade-2.0.0.md">代码模板升级指南</a> ，和查看详细的编写 <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md">模板变量文档</a>**
    - 引入 `velocity/beetl` 模板支持，通过后缀 `ftl/vm/btl` 来自动调用相应模板引擎渲染
    - 重构模板变量对象，引入 `TableImpl/EntityImpl` `TableColumnImpl/EntityFieldImpl` 四个对象来存储相关信息。
    - `types.json` 支持正则表达式匹配数据库字段类型。
    - UI显示字段信息增加 数据库列-Java字段 的结果对比显示
    - 模板支持 `include` 指令
    - **&gt;&gt; EN**
    - **This version is not compatible with the old version, please check the <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/upgrade-2.0.0.md">Code Template Upgrade Guide</a>, and check the detailed writing <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md">Template Variable Document</a>**
    - Introduce `velocity/beetl` template support, through the suffix `ftl/vm/btl` to automatically call the corresponding template engine for rendering
    - Refactor the template variable object and introduce four objects `TableImpl/EntityImpl` `TableColumnImpl/EntityFieldImpl` to store related information.
    - `types.json` supports regular expression matching database field types.
    - The UI display field information is increased. The database column-Java field result comparison display
    - Template support `include` directive
    
    

<a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/changeNotes.md">Full Log</a> | <a href="https://github.com/houkunlin/Database-Generator/blob/master/doc/changeNotes.md">完整日志</a> 
