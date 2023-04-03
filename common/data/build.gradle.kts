val springBootVersion: String by project
val postgresqlClientVersion: String by project

plugins {
    id("spring-app-module-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.postgresql:postgresql:$postgresqlClientVersion")}
