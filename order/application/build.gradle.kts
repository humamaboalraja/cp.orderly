val springBootVersion: String by project

plugins {
    id("spring-app-module-conventions")
}

dependencies {
    api(project(":common:application"))
    api(project(":order:domain:application-service"))
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
}
