plugins {
    id("org.jetbrains.intellij") version "0.4.17"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    java
    idea
    kotlin("jvm") version "1.3.61"
}

group = "com.github.houkunlin"
version = "1.0-SNAPSHOT"

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
    implementation("jalopy:jalopy:1.5rc3")


    testCompile("junit", "junit", "4.12")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.12")
    testCompileOnly("org.projectlombok:lombok:1.18.12")
    annotationProcessor("org.projectlombok:lombok:1.18.12")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "IU-2019.3.3"
    setPlugins("DatabaseTools", "coverage", "java")
}

//publishPlugin {
//    token = "perm:MTU1Nzc0MDU2Njc=.OTItMTk1Mg==.YXtT4j5WklZRpkbPZltPvSp6kGlcl2" // 1
//    token = "perm:MTU1Nzc0MDU2Njc=.OTItMTk2MQ==.Rg1n4G6qdS6h0MxIODApZB8VtQS9VF" // 2
//}
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
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    setPluginId("com.github.houkunlin.database.generator")
    changeNotes(
        """
        初步完成插件内容。
        """.trimIndent()
    )
    setPluginDescription(
        """
        一个代码生成器，通过数据库表结构简单的生成增删查改代码。<br>
        可通过自定义模板来生成所需要的代码信息。<br><br>
        插件初次运行时会在当前目录下创建 generator 目录用来存放插件所需要的信息，其中 generator/templates 目录中存放了所需要生成的代码模板文件，模板采用 freemarker 来渲染，因此在模板中可以使用 freemarker 的一切特性。
        
        <p>
        作者：侯坤林 <br>
        Email：1184511588@qq.com <br>
        Github：<a href="https://github.com/houkunlin">houkunlin</a> <br>
        Gitee：<a href="https://gitee.com/houkunlin">houkunlin</a> <br>
        </p>
        """.trimIndent()
    )
}
