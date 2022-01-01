import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

android {
    compileSdk= Config.Android.compileSdk
    defaultConfig {
        applicationId = Config.Release.applicationID
        minSdk = Config.Android.minSdk
        targetSdk = Config.Android.targetSdk
        versionCode = Config.Release.appVersionCode
        versionName = Config.Release.appVersionName

        val propertiesFile = file("local.properties")
        val properties = Properties()
        if (propertiesFile.exists()) {
            properties.load(propertiesFile.inputStream())
        }

        buildConfigField("String", "DROPBOX_APP_KEY", properties.getProperty("dropbox.app_key") ?: "\"\"")
        resValue("string", "dropbox_app_key_schema", properties.getProperty("dropbox.app_key_schema") ?: "\"\"")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = Config.Java.javaVersion
        targetCompatibility = Config.Java.javaVersion
    }

    kotlinOptions {
        jvmTarget = Config.Java.javaVersionNumber
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildFeatures { compose = true }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
}

dependencies {
    implementation(project(":shared"))

    with(Deps.Compose) {
        implementation(core)
        implementation(foundation)
        implementation(layout)
        implementation(material)
        implementation(materialIconsExtended)
        implementation(materialIconsCore)
        implementation(runtime)
        implementation(tooling)
        implementation(composeNavigation)
        implementation(activityCompose)
        implementation(paging)
    }

    implementation(Deps.viewModelKTX)
    implementation(Deps.Koin.core)
    implementation(Deps.Koin.android)
    implementation(Deps.Koin.compose)
    implementation(Deps.timber)
    implementation(Deps.dropboxCore)
    implementation(Deps.biometric)


    androidTestImplementation(Deps.Compose.uiTest)

    debugImplementation(Deps.Compose.tooling)
    debugImplementation(Deps.kotlinReflect)
}