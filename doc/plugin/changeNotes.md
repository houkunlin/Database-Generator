- **2020-08-21: 2.3.0**

    - **&gt;&gt; ZH-CN**
    - feat: 包名、路径配置支持持久化
    - feat: 如果未选中模板时弹出警告，提示至少选中一个代码模板
    - feat: 界面逻辑代码优化
    - **&gt;&gt; EN**
    - feat: Package name and path configuration support persistence
    - feat: If a warning pops up when the template is not selected, it prompts that at least one code template is selected
    - feat: interface logic code optimization
    
- **2020-08-17: 2.2.0**

    - **&gt;&gt; ZH-CN**
    - feat: 更改UI布局
    - feat: 项目路径选择输入框更改输入框组件
    - feat: 包名输入框增加包名自动补全、提示
    - feat: 更改输入框内容修改后重新赋值的方式，通过监听事件来改变配置信息内容
    - feat: 增加代码模板选择功能，通过树形结构选择器选择当前项目路径下可用的代码模板文件
    - **&gt;&gt; EN**
    - feat: Change UI layout
    - feat: project path selection input box to change the input box component
    - feat: Add package name auto completion and prompt to the package name input box
    - feat: change the way of re-assignment after modification of the input box content, and change the configuration information content by monitoring events
    - feat: Add code template selection function, select the available code template files under the current project path through the tree structure selector

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