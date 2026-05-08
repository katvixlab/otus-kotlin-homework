plugins{
    id("build-jvm")
}

dependencies {
    implementation(project(":ok-coachdesk-common"))
    implementation(projects.okCoachdeskStub)
}