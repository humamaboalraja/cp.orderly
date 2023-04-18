import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.diffplug.spotless")
    kotlin("jvm")
    kotlin("plugin.allopen")
    kotlin("plugin.noarg")
}


configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin { ktlint() }
    kotlinGradle { ktlint() }
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            javaParameters = true
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}
