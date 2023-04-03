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
    "common:application",
    "common:data",
    "common:test-coverage-report",

    "infrastructure",
    "infrastructure:kafka",
    "infrastructure:kafka:consumer",
    "infrastructure:kafka:model",
    "infrastructure:kafka:producer",
    "infrastructure:kafka:config",

    "order:domain:core",
    "order:domain:application-service",
    "order:application",
    "order:data",
    "order:messaging",
    "order:main",
    "order:test-coverage-report",

    "customer:main",

    "payment:domain:core",
    "payment:domain:application-service",
    "payment:data",
    "payment:main",

    "shop:domain:core",
    "shop:domain:application-service",
    "shop:data",
)
