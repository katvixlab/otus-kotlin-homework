plugins {
    id("build-jvm")
}

repositories {
    google()
    mavenCentral()
}

dependencies{
    implementation(libs.coroutines.core)
    implementation(libs.db.postgres)
    implementation(libs.bundles.exposed)

    implementation(projects.okCoachdeskCommon)
    api(projects.okCoachdeskRepoCommon)
    
    testImplementation(kotlin("test-junit"))
    testImplementation(projects.okCoachdeskRepoTests)
    testImplementation(libs.logback)
    testImplementation("org.liquibase:liquibase-core:5.0.3")
    testImplementation("org.postgresql:postgresql:42.7.2")
    testImplementation("org.testcontainers:testcontainers:2.0.5")
    testImplementation("org.testcontainers:postgresql:1.21.4")
    testImplementation("org.testcontainers:junit-jupiter:1.21.4")
}