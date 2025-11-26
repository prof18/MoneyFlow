import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.triplet.play)
}

apply(from = "../versioning.gradle.kts")

val appVersionCode: () -> Int by extra
val appVersionName: () -> String by extra

val local = Properties()
val localProperties: File = rootProject.file("keystore.properties")
if (localProperties.exists()) {
    localProperties.inputStream().use { local.load(it) }
}

val appVersionCode: String by project
val appVersionName: String by project
val javaVersion: JavaVersion by rootProject.extra

android {
    namespace = "com.prof18.moneyflow.androidApp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.prof18.moneyflow"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = appVersionCode()
        versionName = appVersionName()
    }

    signingConfigs {
        create("release") {
            keyAlias = local.getProperty("keyAlias")
            keyPassword = local.getProperty("keyPassword")
            storeFile = file(local.getProperty("storeFile") ?: "NOT_FOUND")
            storePassword = local.getProperty("storePassword")
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    buildFeatures { compose = true }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.koin.android)
    implementation(libs.androidx.biometric.ktx)

    debugImplementation(libs.compose.ui.tooling)
}


play {
    // The play_config.json file will be provided on CI
    serviceAccountCredentials.set(file("../play_config.json"))
    track.set("internal")
}
