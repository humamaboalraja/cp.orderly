
val springBootVersion: String by project

plugins {
    id("kotlin-spock-conventions")
    id("spring-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot:$springBootVersion")

    // implementation(project(":infrastructure:restAssured"))
}
