plugins {
    id("build-jvm")
}

dependencies {
    implementation(project(":ok-coachdesk-common"))
    implementation(project(":ok-coachdesk-repo-common"))
    implementation(libs.coroutines.core)
    implementation(libs.db.cache4k)
}