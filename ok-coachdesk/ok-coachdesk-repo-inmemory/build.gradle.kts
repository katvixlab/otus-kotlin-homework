plugins {
    id("build-jvm")
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.db.cache4k)

    implementation(projects.okCoachdeskCommon)
    implementation(projects.okCoachdeskRepoCommon)
    implementation(projects.okCoachdeskRepoTests)
}

tasks.test {
    useJUnit()
    setScanForTestClasses(false)
}
