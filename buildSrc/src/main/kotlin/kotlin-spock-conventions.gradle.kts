import gradle.kotlin.dsl.accessors._331b34a9e244f7b4ac97fe1de3a121b6.testing

plugins {
    groovy
    kotlin
    id("kotlin-conventions")
    id("jacoco")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHTML"))
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}
