plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.okCoachdeskApiV1Jackson)
    implementation(projects.okCoachdeskCommon)

    testImplementation(kotlin("test-junit"))
}
