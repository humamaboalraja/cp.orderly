val springBootVersion: String by project

plugins {
    id("spring-conventions")
}

dependencies {
    api(project(":infrastructure:transactions:llt"))
    api(project(":infrastructure:transactions:consistency"))
    api(project(":common:domain"))
    api(project(":shop:domain:core"))
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework:spring-tx:6.0.0")
    api("org.springframework.boot:spring-boot-starter-json:$springBootVersion")
}
