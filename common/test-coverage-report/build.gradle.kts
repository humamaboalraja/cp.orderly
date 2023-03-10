plugins {
    id("kotlin-tests-conventions")
}

tasks {
    jacocoTestReport {
        dependsOn(
            ":common:domain:test",
        )
        val domainCommonUtils = file("../domain/build/jacoco/test.exec")

        executionData.setFrom(listOf(domainCommonUtils))
        sourceDirectories.setFrom(
            project(":common:domain").sourceSets.getByName("main").output

        )
        classDirectories.setFrom(
            project(":common:domain").sourceSets.getByName("main").allSource
        )
        additionalClassDirs.setFrom(
            project(":common:domain").sourceSets.getByName("main").output
        )
        additionalSourceDirs.setFrom(
            project(":common:domain").sourceSets.getByName("main").allSource

        )
        sourceSets(
            project(":common:domain").sourceSets.getByName("main"),
        )
    }
}
