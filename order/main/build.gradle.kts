val springBootVersion: String by project

plugins { id("spring-app-webapp-conventions") }

dependencies {
    api(project(":order:domain:core"))
    api(project(":order:domain:application-service"))
    api(project(":order:application"))
    api(project(":order:data"))
    api(project(":order:messaging"))
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}
