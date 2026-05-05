plugins {
    id("build-jvm")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    api(libs.kotlinx.datetime)
    api("ru.otus.kotlin.coachdesk.libs:ok-coachdesk-libs-logging-common")

    testImplementation(kotlin("test-junit"))
}