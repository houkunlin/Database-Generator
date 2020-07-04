plugins {
    id("org.jetbrains.intellij") version "0.4.18"
    java
    idea
    kotlin("jvm") version "1.3.71"
}
// intellij 版本（编译环境版本）
val intellijVersion = when {
    System.getProperty("intellijVersion") != null -> { // 从系统环境变量中获取编译环境版本
        System.getProperty("intellijVersion")
    }
    project.properties["intellijVersion"] != null -> { // 从项目配置中获取编译环境版本
        project.properties["intellijVersion"]
    }
    else -> {// 默认编译环境版本
        "2020.1"
    }
}
// intellij 上传插件 Token
val intellijPublishToken = when {
    System.getProperty("intellijPublishToken") != null -> { // 从系统环境变量中获取上传插件 Token
        System.getProperty("intellijPublishToken")
    }
    project.properties["intellijPublishToken"] != null -> { // 从项目配置中获取上传插件 Token
        project.properties["intellijPublishToken"]
    }
    else -> { // 默认上传插件 Token
        ""
    }
}
// 插件版本
val pluginVersion = when {
    System.getProperty("pluginVersion") != null -> { // 从系统环境变量中获取插件版本
        System.getProperty("pluginVersion")
    }
    project.properties["pluginVersion"] != null -> { // 从项目配置中获取插件版本
        project.properties["pluginVersion"]
    }
    else -> { // 默认的插件版本
        "1.3.1"
    }
}

group = "com.github.houkunlin"
version = "${pluginVersion}-${intellijVersion}"

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public")
    maven("https://repo1.maven.org/maven2")
    mavenCentral()
}
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    //    implementation(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:29.0-jre")
    // https://mvnrepository.com/artifact/org.freemarker/freemarker
    implementation("org.freemarker:freemarker:2.3.30")
    // https://mvnrepository.com/artifact/org.apache.velocity/velocity
    implementation("org.apache.velocity:velocity:1.7")
    // https://mvnrepository.com/artifact/org.apache.velocity/velocity-engine-core
    implementation("org.apache.velocity:velocity-engine-core:2.2")
    // https://mvnrepository.com/artifact/com.ibeetl/beetl
    implementation("com.ibeetl:beetl:3.1.6.RELEASE")

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
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "utf-8"
    options.compilerArgs = listOf("-Xlint:deprecation", "-Xlint:unchecked")
    dependsOn("buildSyncFiles")
}

tasks.getByName<org.jetbrains.intellij.tasks.PublishTask>("publishPlugin") {
    // 在 gradle.properties 文件中设置 intellijPublishToken 属性存储 Token 信息
    // https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html
    setToken(intellijPublishToken)
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    setPluginId("com.github.houkunlin.database.generator")
    changeNotes(File("changeNotes.html").readText())
    setPluginDescription(File("pluginDescription.html").readText())
}

/**
 * 生成插件运行时需要同步的模板文件列表
 */
task("buildSyncFiles") {
    var getFileTree: (File) -> ArrayList<File> = { ArrayList() }
    getFileTree = { path: File ->
        val list = ArrayList<File>()
        val listFiles = path.listFiles() ?: emptyArray()
        listFiles.forEach {
            if (it.isFile) {
                list.add(it)
            } else {
                list.addAll(getFileTree(it))
            }
        }
        list
    }
    val saveFile = File("src/main/resources/syncFiles.txt")
    val files = getFileTree(File("src/main/resources/templates"))
    val filesText = files.map {
        it.path.replace("\\", "/").replace("src/main/resources/", "")
    }.joinToString("\n")
    saveFile.writeText(filesText)
    println(filesText)
}
