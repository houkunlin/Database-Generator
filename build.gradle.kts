plugins {
    id("org.jetbrains.intellij") version "0.4.18"
    id("org.kordamp.gradle.markdown") version "2.2.0"
    java
    idea
    kotlin("jvm") version "1.4.31"
}

/**
 * 获取配置信息。优先从系统环境变量中获取值，再从项目配置中获取值，最后才使用默认值
 *
 * @param key 键
 * @param defaultValue 默认值
 */
fun getProperty(key: String, defaultValue: String): String {
    return when {
        System.getProperty(key) != null -> { // 从系统环境变量中获取值
            System.getProperty(key)
        }
        project.properties[key] != null -> { // 从项目配置中获取值
            project.properties[key] as String
        }
        else -> {// 默认值
            defaultValue
        }
    }
}

// intellij 版本（编译环境版本）
val intellijVersion: String = getProperty("intellijVersion", "2020.2")
// intellij 上传插件 Token
val intellijPublishToken: String = getProperty("intellijPublishToken", "")
// 插件版本
val pluginVersion: String = getProperty("pluginVersion", "2.5.0")

group = "com.github.houkunlin"
version = pluginVersion

println(">>> PROJECT INFO : $group --> { intellij-version = IU-$intellijVersion, intellij-publish-token = ${intellijPublishToken.isNotBlank()}, plugin-version = $version }")

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
    // implementation("com.google.guava:guava:29.0-jre")
    // https://mvnrepository.com/artifact/org.freemarker/freemarker
    implementation("org.freemarker:freemarker:2.3.31")
    // https://mvnrepository.com/artifact/org.apache.velocity/velocity
    // implementation("org.apache.velocity:velocity:1.7")
    // https://mvnrepository.com/artifact/org.apache.velocity/velocity-engine-core
    // implementation("org.apache.velocity:velocity-engine-core:2.2")
    // https://mvnrepository.com/artifact/com.ibeetl/beetl
    implementation("com.ibeetl:beetl:3.3.2.RELEASE")
    implementation("joda-time:joda-time:2.10.10")

    // implementation("com.google.code.gson:gson:2.8.5")
    // https://mvnrepository.com/artifact/jalopy/jalopy
    // implementation("jalopy:jalopy:1.5rc3")

    testImplementation("junit:junit:4.13")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.12")
    testCompileOnly("org.projectlombok:lombok:1.18.12")
    annotationProcessor("org.projectlombok:lombok:1.18.12")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "IU-${intellijVersion}"
    setPlugins("DatabaseTools", "java")
    sandboxDirectory = "${rootProject.rootDir}/idea-sandbox"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<JavaCompile> {
    dependsOn("buildSyncFiles")
    options.encoding = "utf-8"
    options.compilerArgs = listOf("-Xlint:deprecation", "-Xlint:unchecked")
}

tasks.getByName<org.jetbrains.intellij.tasks.PublishTask>("publishPlugin") {
    // 在 gradle.properties 文件中设置 intellijPublishToken 属性存储 Token 信息
    // https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html
    setToken(intellijPublishToken)
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    outputs.upToDateWhen { false }
    dependsOn("markdownToHtml")
    setPluginId("com.github.houkunlin.database.generator")
    // 最小支持版本
    setSinceBuild("181")
    // 最大支持版本
    setUntilBuild(null)
    doFirst {
        val notes = file("$buildDir/gen-html/changeNotes.html")
        val desc = file("$buildDir/gen-html/description.html")
        if (notes.exists() && notes.isFile) {
            changeNotes(notes.readText())
        }
        if (desc.exists() && desc.isFile) {
            setPluginDescription(desc.readText())
        }
    }
}

tasks.getByName<org.kordamp.gradle.plugin.markdown.tasks.MarkdownToHtmlTask>("markdownToHtml") {
    sourceDir = file("doc/plugin")
    outputDir = file("$buildDir/gen-html")
}

/**
 * 生成插件运行时需要同步的模板文件列表
 */
task("buildSyncFiles") {
    outputs.upToDateWhen { false }
    dependsOn("markdownToHtml")
    val sourcePath = "src/main/resources/"

    val dir = arrayOf("config/", "templates/")
    val source = File(rootProject.rootDir, sourcePath)
    val start = source.absolutePath.length

    val filesText = fileTree(sourcePath)
        .map { it.absolutePath.substring(start + 1).replace("\\", "/") }
        .filter { path ->
            dir.any { path.startsWith(it) }
        }
        .joinToString("\n")

    file("${sourcePath}/syncFiles.txt").writeText(filesText)
}
