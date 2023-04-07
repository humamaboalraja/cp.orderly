val springBootVersion: String by project

plugins {
    id("spring-app-module-conventions")
}

dependencies {
    api(project(":customer:domain:core"))
    api(project(":common:domain"))
    implementation("org.springframework:spring-tx:6.0.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
}
