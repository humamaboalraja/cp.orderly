val springBootVersion: String by project

plugins {
    id("spring-app-module-conventions")
}

dependencies {
    api(project(":order:domain:core"))
    api(project(":common:domain"))
    implementation("org.springframework:spring-tx:6.0.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.5")
}
