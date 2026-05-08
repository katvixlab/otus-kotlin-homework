plugins {
    id("build-jvm")
}

dependencies{
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.coroutines.core)

    implementation(libs.coroutines.test)
    implementation(kotlin("test-junit"))
}

