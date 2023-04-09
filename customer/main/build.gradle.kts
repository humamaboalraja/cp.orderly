
val springBootVersion: String by project
val postgresqlClientVersion: String by project

plugins {
    id("spring-app-webapp-conventions")
}

configurations {
}

dependencies {
    api(project(":customer:domain:core"))
    api(project(":customer:domain:application-service"))
    api(project(":customer:data"))
    api(project(":customer:application"))
//    api(project(":customer:messaging"))
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
}
