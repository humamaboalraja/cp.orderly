val springBootVersion: String by project

plugins { id("spring-app-webapp-conventions") }

dependencies {
    api(project(":order:domain:core"))
    api(project(":order:domain:application-service"))
    api(project(":order:application"))
    api(project(":order:data"))
    api(project(":order:messaging"))
    api(project(":common:data"))
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
}
