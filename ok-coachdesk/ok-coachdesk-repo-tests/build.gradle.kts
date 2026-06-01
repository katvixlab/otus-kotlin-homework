plugins {
    id("build-jvm")
}

dependencies {
    api(libs.coroutines.core)
    api(libs.coroutines.test)
    implementation(projects.okCoachdeskCommon)
    implementation(projects.okCoachdeskRepoCommon)

    api(kotlin("test-common"))
    api(kotlin("test-annotations-common"))
    api(kotlin("test-junit"))
}