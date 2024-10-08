
import com.varabyte.kobweb.gradle.application.extensions.AppBlock
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.serialization.plugin)
}

group = "com.yogaveda"
version = "1.0-SNAPSHOT"

kobweb {
    app {

        val globalEntries = HashMap<String, String>().apply {
            put("encryptionkey", "yogavedaencryptionkey")
        }
        globals.putAll(globalEntries)

        index {
            description.set("Powered by Yogaveda with ❤\uFE0F")

            head.add {
                script {
                    src = "/highlight.min.js"
                }
                link {
                    rel = "stylesheet"
                    href = "/github-dark.min.css"
                }
                script {
                    src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                }
                link {
                    rel = "stylesheet"
                    href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
                }
                link {
                    rel = "preconnect"
                    href = "https://fonts.googleapis.com"
                }
                link {
                    rel = "preconnect"
                    href = "https://fonts.gstatic.com"
                    type = "crossorigin"
                }
                link {
                    rel = "stylesheet"
                    href = "https://fonts.googleapis.com/css2?family=Archivo:ital,wght@0,100..900;1,100..900&family=Gotu&family=League+Script&display=swap"
                }
            }
        }
    }
}

kotlin {

    configAsKobwebApplication("yogaveda", includeServer = true)

    this.kotlinDaemonJvmArgs = listOf("-Xexpect-actual-classes")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(libs.kotlinx.serialization)
                implementation(project(":shared"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk.core)
                implementation(libs.kobweb.silk.icons.fa)
                implementation(libs.kotlinx.serialization)
                implementation(libs.firebase.auth)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.kobweb.api)
                implementation(libs.mongodb.kotlin)
                 implementation(libs.kotlinx.serialization)
            }
        }
    }
}