val springBootVersion: String by project
plugins {
    id("spring-conventions")
}

dependencies {
    implementation(project(":shop:domain:core"))
    implementation(project(":shop:domain:application-service"))
    implementation(project(":shop:data"))
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
}
