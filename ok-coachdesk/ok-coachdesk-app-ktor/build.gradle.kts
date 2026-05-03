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
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:1.5.32")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.3.20")
}

tasks.register("prepareKotlinBuildScriptModel"){}
