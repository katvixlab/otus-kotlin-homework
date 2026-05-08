plugins{
    id("build-jvm")
}

dependencies {
    implementation(project(":ok-coachdesk-common"))
    implementation(project(":ok-coachdesk-biz"))
    implementation(project(":ok-coachdesk-api-log"))

}
