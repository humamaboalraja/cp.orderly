import java.io.FileInputStream
import java.util.Properties

val props = Properties()
FileInputStream(file("../gradle.properties")).use {
    props.load(it)
}

plugins {
    val kotlinVersion = "1.6.21"

    `kotlin-dsl`
    id("org.springframework.boot") version "2.6.7" apply false
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    repositories {
        mavenCentral()
        maven(url = "https://repo.spring.io/milestone")
    }
}

repositories {
    gradlePluginPortal()
}

allprojects {
    group = "co.cp.orderly"
    version = "0.0.1-SNAPSHOT"
}



dependencies {
    val kotlinVersion = props.getProperty("kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    val springBootVersion = props.getProperty("springBootVersion")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")

    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.2.0")

    implementation("org.jacoco:org.jacoco.core:0.8.7")
}

tasks.register<JacocoReport>("codeCoverageReport") {
    executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))
    dependsOn(allprojects.map { it.tasks.named<Test>("test") })
}
