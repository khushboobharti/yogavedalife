plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kobweb.library) apply false
    alias(libs.plugins.serialization.plugin) apply false
    alias(libs.plugins.mongodb.realm) apply false
    alias(libs.plugins.google.services) apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

subprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
    }
}