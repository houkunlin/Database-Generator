plugins {
    id("org.jetbrains.intellij") version "0.4.18"
    java
    idea
    kotlin("jvm") version "1.3.71"
}
// intellij 版本
val intellijVersion = project.properties["intellijVersion"] ?: "2019.3.4"

group = "com.github.houkunlin"
version = "1.3-${intellijVersion}"

repositories {
    mavenCentral()
}
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    //    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.guava:guava:27.1-jre")
    // https://mvnrepository.com/artifact/org.freemarker/freemarker
    implementation("org.freemarker:freemarker:2.3.30")
    implementation("com.google.code.gson:gson:2.8.5")
    // https://mvnrepository.com/artifact/jalopy/jalopy
    // implementation("jalopy:jalopy:1.5rc3")

    testCompile("junit", "junit", "4.12")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.12")
    testCompileOnly("org.projectlombok:lombok:1.18.12")
    annotationProcessor("org.projectlombok:lombok:1.18.12")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "IU-${intellijVersion}"
    setPlugins("DatabaseTools", "coverage", "java")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileJava {
        options.encoding = "UTF-8"
        options.compilerArgs = listOf("-Xlint:deprecation", "-Xlint:unchecked")
    }
    compileTestJava {
        options.encoding = "UTF-8"
        options.compilerArgs = listOf("-Xlint:deprecation", "-Xlint:unchecked")
    }
}

tasks.getByName<org.jetbrains.intellij.tasks.PublishTask>("publishPlugin") {
    // 在 gradle.properties 文件中设置 intellijPublishToken 属性存储 Token 信息
    // https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html
    setToken(project.properties["intellijPublishToken"])
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    setPluginId("com.github.houkunlin.database.generator")
    changeNotes(
        """
        <ul>
        <li>
            <strong>2020-04-10</strong>
            <ul>
            <li>支持2020.1/ support 2020.1</li>
            <li>fix: 修复修改数据库表描述信息时返回构建选项tab时错误问题 / Fixed an error when returning the build option tab when modifying database table description information</li>
            </ul>
        </li>
        <li>docs: 修改插件描述信息/Modify plug-in description information</li>
        <li>feat: 表注释可修改/Table notes can be modified</li>
        <li>feat: 支持多表同时生成/Support simultaneous generation of multiple tables</li>
        <li>初步完成插件内容。/Preliminary completion of plug-in content</li>
        </ul>
        """.trimIndent()
    )
    setPluginDescription(
        """
        <p>
        <a href="https://github.com/houkunlin/Database-Generator">Github</a> | <a href="https://gitee.com/houkunlin/Database-Generator">Gitee</a>
        </p>
        <br>
        <strong>English (Google Translate)</strong> / 中文
        <p>A code generator that relies on the IDEA Database tool to generate add, delete, check, modify, and function codes simply through the database table structure.</p>
        <p>You can use custom templates to generate the required code information.</p>        
        <br>
        <p>
        When the plugin is run for the first time, the generator directory will be created in the current directory to store the information required by the plugin. The generator / templates directory contains the code template files that need to be generated.
         The template is rendered with freemarker, so all the features of freemarker can be used in the template.
        </p>
        <p>
        The template customizes the &lt;@gen /&gt; instruction, which can accept 3 parameters type/filename/filepath。
        </p>
        <ul>
            <li> type : entity / dao / service / serviceImpl / controller / xml / other</li>
            <li> filename ：Will override the default file name configuration: EntityName + Suffix + '.java'</li>
            <li> filepath : Will override the default file path configuration: javaPath/sourcePath + package</li>
        </ul>
        <p>
        In general, use &lt;@gen type="entity" /&gt; to mark the type of the current file, Then use some default configuration (file name, package name, save path) according to the file type,
        If the default configuration (file name, package name, save path) is not applicable, you can use &lt;@gen filename="${'$'}{table.entityName}DTO.java" filepath="src/main/java/com.example.domain.dto" /&gt; to override the default configuration
        </p>
        <br>
        <br>
        <strong>中文</strong> / English
        <p>一个依赖 IDEA Database 工具的代码生成器，通过数据库表结构简单的生成增、删、查、改、功能代码。</p>
        <p>可通过自定义模板来生成所需要的代码信息。</p>        
        <br>
        <p>
        插件初次运行时会在当前目录下创建 generator 目录用来存放插件所需要的信息，其中 generator/templates 目录中存放了所需要生成的代码模板文件，
        模板采用 freemarker 来渲染，因此在模板中可以使用 freemarker 的一切特性。
        </p>
        <p>
        模板自定义了 &lt;@gen /&gt; 指令，可接受3个参数 type/filename/filepath。
        </p>
        <ul>
            <li> type : entity / dao / service / serviceImpl / controller / xml / other</li>
            <li> filename ：会覆盖默认的文件名配置：EntityName + Suffix + '.java'</li>
            <li> filepath : 会覆盖默认的文件路径配置：javaPath/sourcePath + package</li>
        </ul>
        <p>
        一般情况下使用 &lt;@gen type="entity" /&gt; 来标记当前文件的类型，然后根据文件类型来使用一些默认的配置（文件名、包名、保存路径），
        如果不适用默认的配置（文件名、包名、保存路径），则可以使用 &lt;@gen filename="${'$'}{table.entityName}DTO.java" filepath="src/main/java/com.example.domain.dto" /&gt; 来覆盖默认配置
        </p>
        <br>
        <p>
        Author：HouKunLin <br>
        Email：1184511588@qq.com <br>
        </p>
        """.trimIndent()
    )
}
