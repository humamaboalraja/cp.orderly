val springBootVersion: String by project

plugins {
    id("raw-logic-tests")
}

dependencies {
    api(project(":common:domain"))
    api(project(":payment:domain:core"))
    api(project(":payment:domain:core"))
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework:spring-tx:6.0.0")
}
