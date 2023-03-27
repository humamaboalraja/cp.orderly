
val springBootVersion: String by project
val postgresqlClientVersion: String by project

plugins { id("spring-app-webapp-conventions") }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.postgresql:postgresql:$postgresqlClientVersion")

}

tasks.register<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName.set("${project.group}/order.service:${project.version}")
    archiveFile.set(File("./build/libs/main.jar"))
}
