plugins {
    id("org.jetbrains.intellij") version "0.4.18"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    java
    idea
    kotlin("jvm") version "1.3.61"
}

group = "com.github.houkunlin"
version = "1.1-SNAPSHOT"

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
    version = "IU-2019.3.4"
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
//    setToken(System.getenv("intellijPublishToken"))
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    setPluginId("com.github.houkunlin.database.generator")
    changeNotes(
        """
        <ul>
        <li>feat: 表注释可修改</li>
        <li>feat: 支持多表同时生成</li>
        <li>初步完成插件内容。</li>
        </ul>
        """.trimIndent()
    )
    setPluginDescription(
        """
        <p>
        <a href="https://github.com/houkunlin/Database-Generator">Github</a> | <a href="https://gitee.com/houkunlin/Database-Generator">Gitee</a>
        </p>
        <br>
        <p>一个依赖 IDEA Database 工具的代码生成器，通过数据库表结构简单的生成增删查改代码。</p>
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
        作者：侯坤林 <br>
        Email：1184511588@qq.com <br>
        </p>
        """.trimIndent()
    )
}
