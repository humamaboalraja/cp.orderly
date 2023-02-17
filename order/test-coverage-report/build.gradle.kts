plugins {
    id("kotlin-spock-conventions")
}

tasks {
    jacocoTestReport {
        dependsOn(
            ":order:domain:application-service:test"
        )
        val mainExecData = file("../domain/application-service/build/test.exec")

        executionData.setFrom(listOf(mainExecData))
        sourceDirectories.setFrom(
            project(":order:domain:application-service").sourceSets.getByName("main").output
        )
        classDirectories.setFrom(
            project(":order:domain:application-service").sourceSets.getByName("main").allSource
        )
        additionalClassDirs.setFrom(
            project(":order:domain:application-service").sourceSets.getByName("main").output
        )
        additionalSourceDirs.setFrom(
            project(":order:domain:application-service").sourceSets.getByName("main").allSource
        )
        sourceSets(
            project(":order:domain:application-service").sourceSets.getByName("main"),
        )
    }
}
