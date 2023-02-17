import gradle.kotlin.dsl.accessors._331b34a9e244f7b4ac97fe1de3a121b6.testing

plugins {
    groovy
    kotlin
    id("kotlin-conventions")
    id("jacoco")
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}
