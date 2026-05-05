plugins {
    id("build-jvm")
}

dependencies {
    implementation(projects.okCoachdeskLibsLoggingCommon)
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.logback)
    implementation(libs.logback.logstash)
    implementation(libs.logback.appenders)
    implementation(libs.logger.fluentd)

    testImplementation(kotlin("test-junit"))
}
