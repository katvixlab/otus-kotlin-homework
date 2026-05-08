plugins{
    id("build-jvm")
}

dependencies {
    implementation(projects.okCoachdeskCommon)
    implementation(projects.okCoachdeskBiz)
    implementation(projects.okCoachdeskApiLog)
}
