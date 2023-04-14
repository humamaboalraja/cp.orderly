val springBootVersion: String by project

plugins {
    id("spring-app-module-conventions")
}

dependencies {
    api(project(":infrastructure:transactions:consistency"))
    api(project(":infrastructure:transactions:llt"))
    api(project(":order:domain:core"))
    api(project(":common:domain"))
    implementation("org.springframework:spring-tx:6.0.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-json:$springBootVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.mockito:mockito-core:5.2.0")
}
