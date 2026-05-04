plugins {
    id("build-jvm")
    alias(libs.plugins.ktor)
}

group = rootProject.group
version = rootProject.version

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}


dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.calllogging)
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.server.negotiation)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.headers.caching)
    implementation(libs.ktor.server.headers.default)
    implementation(libs.ktor.server.headers.auto.response)
    implementation(libs.ktor.server.yaml)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.cors)
    implementation(libs.logback)

    implementation(project(":ok-coachdesk-app-common"))
    implementation(project(":ok-coachdesk-common"))
    implementation(project(":ok-coachdesk-biz"))
    implementation(project(":ok-coachdesk-api-v1-jackson"))
    api("ru.otus.kotlin.coachdesk.libs:ok-coachdesk-libs-logging-logback")
    api(project(":ok-coachdesk-api-v1-mapper"))

    testImplementation(libs.ktor.client.negotiation)
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.3.20")
}

tasks.register("prepareKotlinBuildScriptModel"){}
