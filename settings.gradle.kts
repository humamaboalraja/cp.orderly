rootProject.name = "orderly"


pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://repo.spring.io/snapshot")
        maven(url = "https://repo.spring.io/milestone")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.springframework.boot") {
                useModule("org.springframework.boot:spring-boot-gradle-plugin:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(
    "common",
    "common:domain",
    "common:test-coverage-report",
    "order:domain:core",
    "order:domain:application-service",
    "order:application",
    "order:data",
    "order:messaging",
    "order:main",
    "order:test-coverage-report"
)
