@Suppress("DSL_SCOPE_VIOLATION") // because of https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.native.cocoapods)
    alias(libs.plugins.android.library)
    id("co.touchlab.kermit")
}

version = "1.0"

kotlin {
    android()
    ios()

    cocoapods {
        summary = "Kotlin Multiplatform Library for Dropbox Api"
        homepage = "https://github.com/prof18/MoneyFlow"
        authors = "Marco Gomiero"
        license = "APACHE"
        ios.deploymentTarget = "15"

        pod("ObjectiveDropboxOfficial")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.touchlab.kermit)
                implementation(libs.kotlinx.coroutine.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.dropbox.dropboxCore)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.junit)
            }
        }
        val iosMain by getting
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
}

val releaseBuild: String by project
kermit {
    if (releaseBuild.toBoolean()) {
        stripBelow = co.touchlab.kermit.gradle.StripSeverity.Info
    }
}