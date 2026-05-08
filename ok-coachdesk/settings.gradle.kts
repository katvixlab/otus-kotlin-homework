rootProject.name = "ok-coachdesk"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

includeBuild("../ok-coachdesk-libs")


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":ok-coachdesk-api-v1-jackson")
include(":ok-coachdesk-api-v1-mapper")
include(":ok-coachdesk-api-log")
include(":ok-coachdesk-common")
include(":ok-coachdesk-biz")
include(":ok-coachdesk-app-ktor")
include(":ok-coachdesk-app-common")
include(":ok-coachdesk-stub")
