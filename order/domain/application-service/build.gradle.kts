val springBootVersion: String by project

plugins {
    id("spring-app-module-conventions")
    kotlin("plugin.lombok") version "1.8.10"
    id("io.freefair.lombok") version "5.3.0"
}

dependencies {
    api(project(":order:domain:core"))
    api(project(":common:domain"))
    implementation("org.springframework:spring-tx:6.0.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
}
