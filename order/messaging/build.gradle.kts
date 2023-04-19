val springBootVersion: String by project
val avroVersion: String by project
plugins {
    id("spring-app-module-conventions")
}

dependencies {
    implementation("org.springframework.kafka:spring-kafka:$springBootVersion")
    api(project(":order:domain:application-service"))
    api(project(":infrastructure:kafka:model"))
    api(project(":infrastructure:kafka:producer"))
    api(project(":infrastructure:kafka:consumer"))
    implementation("org.apache.avro:avro:$avroVersion")
}
