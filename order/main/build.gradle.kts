plugins {
    id("spring-app-webapp-conventions")
}
dependencies {
    api(project(":order:domain:core"))
    api(project(":order:domain:application-service"))
    api(project(":order:application"))
    api(project(":order:data"))
    api(project(":order:messaging"))
}
