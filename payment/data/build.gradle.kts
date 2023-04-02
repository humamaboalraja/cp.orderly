val springBootVersion: String by project
val postgresqlClientVersion: String by project

plugins {
    id("spring-conventions")
}

dependencies {
    api(project(":payment:domain:application-service"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.postgresql:postgresql:$postgresqlClientVersion")
}
