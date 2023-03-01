
val springBootVersion: String by project

plugins {
    id("kotlin-tests-conventions")
    id("spring-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot:$springBootVersion")
    // implementation(project(":x:y"))
}
