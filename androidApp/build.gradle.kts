import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
//    id("kotlin-parcelize")
}

//val propertiesFile = file("local.properties")
//val properties = Properties()
//if (propertiesFile.exists()) {
//    properties.load(propertiesFile.inputStream())
//}

android {
    compileSdk= 31
    defaultConfig {
        applicationId = "com.prof18.moneyflow"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildFeatures { compose = true }
    buildToolsVersion = "30.0.3"
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