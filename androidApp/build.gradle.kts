plugins {
    id("com.android.application")
    kotlin("android")
//    id("kotlin-parcelize")
}


android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.prof18.moneyflow"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    composeOptions {
        kotlinCompilerVersion = "1.4.10"
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildFeatures { compose = true }
    buildToolsVersion = "30.0.2"
}

dependencies {
    implementation(project(":shared"))
    implementation(Deps.stdlib)
    implementation("androidx.core:core-ktx:1.2.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")



    implementation("com.google.android.material:material:1.1.0")

    implementation(Deps.Compose.core)
    implementation(Deps.Compose.foundation)
    implementation(Deps.Compose.layout)
    implementation(Deps.Compose.material)
    implementation(Deps.Compose.materialIconsExtended)
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



//    androidTestImplementation(Deps.Compose.test)
    androidTestImplementation(Deps.Compose.uiTest)


}