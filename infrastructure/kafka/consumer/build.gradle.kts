val springBootVersion: String by project
val kafkaAvroSerializer: String by project
val avroVersion: String by project
plugins {
    id("kotlin-tests-conventions")
    id("spring-conventions")
}

dependencies {
    api(project(":infrastructure:kafka:config"))
    implementation("org.springframework.kafka:spring-kafka:$springBootVersion")
    implementation("org.apache.avro:avro:$avroVersion")

    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
}
