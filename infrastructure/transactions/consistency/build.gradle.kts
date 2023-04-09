val springBootVersion: String by project
plugins {
    id("spring-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
}
