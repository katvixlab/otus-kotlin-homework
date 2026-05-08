plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins{
        register("build-jvm"){
            id = "build-jvm"
            implementationClass = "ru.otus.kotlin.buildplugin.JvmBuild"
        }
        register("build-kmp"){
            id = "build-kmp"
            implementationClass = "ru.otus.kotlin.buildplugin.MyPluginMultiplatform"
        }
    }
}

repositories {
    mavenCentral()
}



dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.kotlin.compatibility.validator)
    testImplementation(kotlin("test-junit5"))
}

