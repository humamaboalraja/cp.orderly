plugins {
    id("kotlin-tests-conventions")
}

tasks {
    jacocoTestReport {
        dependsOn(
            ":infrastructure:kafka:config:test",
            ":infrastructure:kafka:model:test",
            ":infrastructure:kafka:consumer:test",
            ":infrastructure:kafka:producer:test",
        )
        val kafkaConfig = file("../kafka/config/build/jacoco/test.exec")
        val kafkaModel = file("../kafka/model/build/jacoco/test.exec")
        val kafkaConsumer = file("../kafka/consumer/build/jacoco/test.exec")
        val kafkaProducer = file("../kafka/producer/build/jacoco/test.exec")

        executionData.setFrom(listOf(kafkaConfig, kafkaModel, kafkaConsumer, kafkaProducer))
        sourceDirectories.setFrom(
            project(":infrastructure:kafka:config").sourceSets.getByName("main").output +
                project(":infrastructure:kafka:model").sourceSets.getByName("main").output +
                project(":infrastructure:kafka:consumer").sourceSets.getByName("main").output +
                project(":infrastructure:kafka:producer").sourceSets.getByName("main").output

        )
        classDirectories.setFrom(
            project(":infrastructure:kafka:config").sourceSets.getByName("main").allSource +
                project(":infrastructure:kafka:model").sourceSets.getByName("main").allSource +
                project(":infrastructure:kafka:consumer").sourceSets.getByName("main").allSource +
                project(":infrastructure:kafka:producer").sourceSets.getByName("main").allSource
        )
        additionalClassDirs.setFrom(
            project(":infrastructure:kafka:config").sourceSets.getByName("main").output +
                project(":infrastructure:kafka:model").sourceSets.getByName("main").output +
                project(":infrastructure:kafka:consumer").sourceSets.getByName("main").output +
                project(":infrastructure:kafka:producer").sourceSets.getByName("main").output
        )
        additionalSourceDirs.setFrom(
            project(":infrastructure:kafka:config").sourceSets.getByName("main").allSource +
                project(":infrastructure:kafka:model").sourceSets.getByName("main").allSource +
                project(":infrastructure:kafka:consumer").sourceSets.getByName("main").allSource +
                project(":infrastructure:kafka:producer").sourceSets.getByName("main").allSource

        )
        sourceSets(
            project(":infrastructure:kafka:config").sourceSets.getByName("main"),
            project(":infrastructure:kafka:model").sourceSets.getByName("main"),
            project(":infrastructure:kafka:consumer").sourceSets.getByName("main"),
            project(":infrastructure:kafka:producer").sourceSets.getByName("main")
        )
    }
}
