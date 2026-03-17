plugins {
    id("build-jvm")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(kotlin("test-junit"))
}