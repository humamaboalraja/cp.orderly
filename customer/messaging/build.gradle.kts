val springBootVersion: String by project
val avroVersion: String by project
plugins {
    id("spring-app-module-conventions")
}

dependencies {
    api(project(":customer:domain:application-service"))
    api(project(":infrastructure:kafka:model"))
    api(project(":infrastructure:kafka:producer"))
    implementation("org.springframework.kafka:spring-kafka:$springBootVersion")
    implementation("org.apache.avro:avro:$avroVersion")
}
