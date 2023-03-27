plugins {
    id("spring-conventions")
    kotlin("plugin.spring")
}

val springBootVersion: String by project
val restAssuredVersion: String by project
val postgresqlClientVersion : String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.postgresql:postgresql:$postgresqlClientVersion")
    // implementation(project(":x:y"))
}


testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
            dependencies {
                implementation("org.jacoco:org.jacoco.agent:0.8.8")
                implementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
                implementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
                implementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
                implementation("org.awaitility:awaitility:4.2.0")
                implementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
            }
        }
    }
}
