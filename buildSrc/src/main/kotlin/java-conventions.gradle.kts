plugins {
    `java-library`
    `maven-publish`
}

repositories {
    maven { url = uri("https://repo.maven.apache.org/maven2/") }

    maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-logging:3.0.5")
    compileOnly("org.projectlombok:lombok:1.18.26")
}

group = "co.cp.orderly"

version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing { publications.create<MavenPublication>("maven") { from(components["java"]) } }

tasks.withType<JavaCompile>() { options.encoding = "UTF-8" }

tasks.withType<Javadoc>() { options.encoding = "UTF-8" }
