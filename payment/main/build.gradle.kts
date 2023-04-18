val springBootVersion: String by project
plugins {
    id("spring-conventions")
}

dependencies {
    api(project(":payment:domain:core"))
    api(project(":payment:domain:application-service"))
    api(project(":payment:data"))
//    api(project(":payment:messaging"))
    api("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
}
