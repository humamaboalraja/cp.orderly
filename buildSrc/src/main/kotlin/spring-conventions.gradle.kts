
val springBootVersion: String by project

plugins {
    id("kotlin-conventions")
}


dependencies {
    implementation("org.springframework.boot:spring-boot:$springBootVersion")

    // api(project(":core"))
}
