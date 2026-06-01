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
    implementation(libs.ktor.server.websocket)
    implementation(libs.logback)

    implementation(projects.okCoachdeskAppCommon)
    implementation(projects.okCoachdeskCommon)
    implementation(projects.okCoachdeskBiz)
    implementation(projects.okCoachdeskApiV1Jackson)
    implementation(projects.okCoachdeskApiV1Mapper)
    implementation(projects.okCoachdeskRepoInmemory)
    implementation(projects.okCoachdeskRepoCommon)
    implementation(projects.okCoachdeskRepoStub)
    implementation("ru.otus.kotlin.coachdesk.libs:ok-coachdesk-libs-logging-logback")


    testImplementation(libs.ktor.client.negotiation)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.junit)
}

tasks.register("prepareKotlinBuildScriptModel"){}
tasks.register("prepareKotlinIdeaImport"){}
