- **2020-07-18: 2.1.0**
    
    - **&gt;&gt; ZH-CN**
    - feat/fix: 修复无法通过Java字段获取到该字段对应的数据库字段对象问题
    - feat/fix: 修复无法直接获取到主键字段问题
    - **&gt;&gt; EN**
    - feat/fix: Fix the problem that the database field object corresponding to the field cannot be obtained through the Java field
    - feat/fix: Fix the problem that the primary key field cannot be obtained directly
    
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