val springBootVersion: String by project

plugins {
    id("raw-logic-tests")
}

dependencies {
    api(project(":infrastructure:transactions:llt"))
    api(project(":infrastructure:transactions:consistency"))
    api(project(":common:domain"))
    api(project(":payment:domain:core"))
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    api("org.springframework.boot:spring-boot-starter-json:$springBootVersion")
    implementation("org.springframework:spring-tx:6.0.0")
}
