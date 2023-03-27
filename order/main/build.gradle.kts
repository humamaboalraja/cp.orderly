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

 tasks.register<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName.set("${project.group}/order.service:${project.version}")
    archiveFile.set(File("./build/libs/main.jar"))
 }
