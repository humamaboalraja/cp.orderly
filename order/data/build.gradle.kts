val springBootVersion: String by project
val postgresqlClientVersion: String by project
postgresqlClientVersion
plugins {
    id("spring-app-module-conventions")
}

dependencies {
    api(project(":order:domain:application-service"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.postgresql:postgresql:$postgresqlClientVersion")
}
