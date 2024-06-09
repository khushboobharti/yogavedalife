import com.varabyte.kobweb.gradle.library.util.configAsKobwebLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kobweb.library)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization.plugin)
}

kotlin {

    configAsKobwebLibrary(includeServer = true)

    js(IR) { browser() }
    jvm()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization)
                implementation(libs.junit)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk.core)
                implementation(libs.kobweb.silk.icons.fa)
            }
        }

        val jvmMain by getting {
            dependencies {}
        }

        val androidMain by getting {
            dependencies {}
        }
    }

    repositories {
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
    }
}

android {
    namespace = "com.yogaveda.shared"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
