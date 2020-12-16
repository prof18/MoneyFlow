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
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.prof18.moneyflow"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

//        buildConfigField , , )

        val propertiesFile = file("local.properties")
        val properties = Properties()
        if (propertiesFile.exists()) {
            properties.load(propertiesFile.inputStream())
        }

        buildConfigField("String", "DROPBOX_APP_KEY", properties.getProperty("dropbox.app_key") ?: "\"\"")
        resValue("string", "dropbox_app_key_schema", properties.getProperty("dropbox.app_key_schema") ?: "\"\"")

//        resValue "string", "dropbox_app_key_schema", gradle.ext.appProperties.getProperty("dropbox.app_key_schema", '')

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
        jvmTarget = "1.8"
        useIR = true
    }

    composeOptions {
        kotlinCompilerVersion = "1.4.20"
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildFeatures { compose = true }
    buildToolsVersion = "30.0.3"
}

dependencies {
    implementation(project(":shared"))
    implementation(Deps.stdlib)
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")



    implementation("com.google.android.material:material:1.2.1")

    implementation(Deps.Compose.core)
    implementation(Deps.Compose.foundation)
    implementation(Deps.Compose.layout)
    implementation(Deps.Compose.material)
    implementation(Deps.Compose.materialIconsExtended)
    implementation(Deps.Compose.materialIconsCore)
    implementation(Deps.Compose.runtime)
    implementation(Deps.Compose.runtimeLivedata)
    implementation(Deps.Compose.tooling)
    implementation(Deps.Compose.runtimeLiveData)
    implementation(Deps.Compose.composeNavigation)
    implementation(Deps.Koin.core)
    implementation(Deps.Koin.androidViewModel)
    implementation(Deps.Koin.compose)
    implementation(Deps.timber)
    implementation(Deps.activityKTX)
    implementation(Deps.dropboxCore)


//    androidTestImplementation(Deps.Compose.test)
    androidTestImplementation(Deps.Compose.uiTest)


}