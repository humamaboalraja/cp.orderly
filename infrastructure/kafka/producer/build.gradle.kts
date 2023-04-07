val springBootVersion: String by project
val kafkaAvroSerializer: String by project
plugins {
    id("kotlin-tests-conventions")
    id("spring-conventions")
}

// Overwriting project default repositories
repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven")
    }
}

dependencies {
    api(project(":infrastructure:kafka:config"))
    api(project(":infrastructure:kafka:model"))
    implementation(project(":infrastructure:transactions:consistency"))
    api(project(":order:domain:core"))
    api(project(":common:domain"))
    implementation("org.springframework.kafka:spring-kafka:$springBootVersion")
    implementation("io.confluent:kafka-avro-serializer:$kafkaAvroSerializer") {
        exclude(group = "org.slf4j")
        exclude(group = "io.swagger")
    }
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
}
