- **2020-07-18: 2.0.0**
    - **&gt;&gt; ZH-CN**
    - **该版本与旧版本不兼容，请查看 [代码模板升级指南](https://github.com/houkunlin/Database-Generator/blob/master/doc/upgrade-2.0.0.md) ，和查看详细的编写 [模板变量文档](https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md)**
    - 引入 `velocity/beetl` 模板支持，通过后缀 `ftl/vm/btl` 来自动调用相应模板引擎渲染
    - 重构模板变量对象，引入 `TableImpl/EntityImpl` `TableColumnImpl/EntityFieldImpl` 四个对象来存储相关信息。
    - `types.json` 支持正则表达式匹配数据库字段类型。
    - UI显示字段信息增加 数据库列-Java字段 的结果对比显示
    - 模板支持 `include` 指令
    - **&gt;&gt; EN**
    - **This version is not compatible with the old version, please check the [Code Template Upgrade Guide](https://github.com/houkunlin/Database-Generator/blob/master/doc/upgrade-2.0.0.md), and check the detailed writing [Template Variable Document](https://github.com/houkunlin/Database-Generator/blob/master/doc/template-document.md)**
    - Introduce `velocity/beetl` template support, through the suffix `ftl/vm/btl` to automatically call the corresponding template engine for rendering
    - Refactor the template variable object and introduce four objects `TableImpl/EntityImpl` `TableColumnImpl/EntityFieldImpl` to store related information.
    - `types.json` supports regular expression matching database field types.
    - The UI display field information is increased. The database column-Java field result comparison display
    - Template support `include` directive
    
    

[Full Log](https://github.com/houkunlin/Database-Generator/blob/master/doc/changeNotes.md) | [完整日志](https://github.com/houkunlin/Database-Generator/blob/master/doc/changeNotes.md) 