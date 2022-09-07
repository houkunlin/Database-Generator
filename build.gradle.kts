plugins {
    id("org.jetbrains.intellij") version "0.4.18"
    id("org.kordamp.gradle.markdown") version "2.2.0"
    id("io.freefair.lombok") version "6.4.3"
    java
    idea
    application
    kotlin("jvm") version "1.4.31"
}

// intellij 版本（编译环境版本）
val intellijVersion = findProperty("intellijVersion") ?: System.getenv("intellijVersion") ?: "2020.2"
// intellij 上传插件 Token
val intellijPublishToken = findProperty("intellijPublishToken") ?: System.getenv("intellijPublishToken")

group = "com.github.houkunlin"
version = "2.6.2"

println(">>> PROJECT INFO : $group --> { intellij-version = IU-$intellijVersion, plugin-version = $version }")

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
    implementation("org.yaml:snakeyaml:1.30")

    // implementation("com.google.code.gson:gson:2.8.5")
    // https://mvnrepository.com/artifact/jalopy/jalopy
    // implementation("jalopy:jalopy:1.5rc3")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
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
    setSinceBuild("193")
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
