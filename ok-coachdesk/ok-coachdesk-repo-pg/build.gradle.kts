plugins {
    id("build-jvm")
}

repositories {
    google()
    mavenCentral()
}

dependencies{
    implementation(libs.coroutines.core)
    implementation(libs.db.postgres)
    implementation(libs.bundles.exposed)

    implementation(projects.okCoachdeskCommon)
    api(projects.okCoachdeskRepoCommon)
    
    testImplementation(kotlin("test-junit"))
    testImplementation(projects.okCoachdeskRepoTests)
    testImplementation(libs.logback)
    testImplementation(libs.bundles.testcontainers.postgres)
}
