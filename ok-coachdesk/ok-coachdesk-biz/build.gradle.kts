plugins{
    id("build-jvm")
}

dependencies {
    implementation(projects.okCoachdeskCommon)
    implementation(projects.okCoachdeskStub)
    implementation(projects.okCoachdeskRepoTests)
    implementation(libs.cor)

    testImplementation(libs.coroutines.test)
    testImplementation(kotlin("test"))
}