plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
//    id("com.vanniktech.maven.publish")
}

group = property("GROUP").toString()
version = property("VERSION_NAME").toString()

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(projects.libresCore)
    implementation(gradleApi())
    implementation(libs.jackson.xml)
    implementation(libs.jackson.kotlin)
    implementation(libs.kotlinpoet)
    implementation(libs.javacv)
    compileOnly(libs.android.sdk.tools)
    compileOnly(libs.plugin.kotlin)
    compileOnly(libs.plugin.android)
    testImplementation(libs.junit)
}

gradlePlugin {
    plugins {
        create("libres-plugin") {
            id = "io.github.skeptick.libres"
            implementationClass = "io.github.skeptick.libres.plugin.ResourcesPlugin"
        }
    }
}

val libresVersion = tasks.register("libresVersion") {
    val outputDir = file("generated")
    inputs.property("version", version)
    inputs.property("group", group)
    outputs.dir(outputDir)

    doFirst {
        val text = """
            // Generated file. Do not edit!
            package io.github.skeptick.libres
            
            val VERSION = "${project.version}"
            val GROUP = "${project.group}"
        """.trimIndent()

        val versionFile = file("$outputDir/io/github/skeptick/libres/Version.kt")
        versionFile.parentFile.mkdirs()
        versionFile.writeText(text)
    }
}

sourceSets.main.configure {
    kotlin.srcDir(libresVersion)
}

apply(from = "../publish.gradle")