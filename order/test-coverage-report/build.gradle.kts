plugins {
    id("kotlin-tests-conventions")
}

tasks {
    jacocoTestReport {
        dependsOn(
            ":order:domain:application-service:test",
            ":order:domain:core:test"
        )
        val domainApplicationService = file("../domain/application-service/build/test.exec")
        val domainCore = file("../domain/core/build/test.exec")

        executionData.setFrom(listOf(domainApplicationService, domainCore))
        sourceDirectories.setFrom(
            project(":order:domain:application-service").sourceSets.getByName("main").output +
                project(":order:domain:core").sourceSets.getByName("main").output

        )
        classDirectories.setFrom(
            project(":order:domain:application-service").sourceSets.getByName("main").allSource +
                project(":order:domain:core").sourceSets.getByName("main").allSource
        )
        additionalClassDirs.setFrom(
            project(":order:domain:application-service").sourceSets.getByName("main").output +
                project(":order:domain:core").sourceSets.getByName("main").output
        )
        additionalSourceDirs.setFrom(
            project(":order:domain:application-service").sourceSets.getByName("main").allSource +
                project(":order:domain:core").sourceSets.getByName("main").allSource

        )
        sourceSets(
            project(":order:domain:application-service").sourceSets.getByName("main"),
            project(":order:domain:core").sourceSets.getByName("main"),
        )
    }
}
