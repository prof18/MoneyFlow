import java.util.*

@Suppress("DSL_SCOPE_VIOLATION") // because of https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

val propertiesFile = file("$rootDir/local.properties")
val properties = Properties()
if (propertiesFile.exists()) {
    properties.load(propertiesFile.inputStream())
}
val dropboxKey = properties.getProperty("dropbox.app_key") ?: ""

val appVersionCode: String by project
val appVersionName: String by project

val javaVersion: JavaVersion by rootProject.extra

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "com.prof18.moneyflow"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = appVersionCode.toInt()
        versionName = appVersionName

        addManifestPlaceholders(
            mapOf(
                "dropboxKey" to dropboxKey
            )
        )
    }
    buildTypes {
        getByName("release") {
            buildConfigField("String", "DROPBOX_APP_KEY", "\"$dropboxKey\"")
            isMinifyEnabled = false
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "DROPBOX_APP_KEY", "\"$dropboxKey\"")
        }
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }


    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildFeatures { compose = true }
    namespace = "com.prof18.moneyflow"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
        jvmTarget = libs.versions.java.get()
    }
}

dependencies {
    implementation(project(":shared"))

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.bundles.koin)
    implementation(libs.bundles.compose)
    implementation(libs.androidx.lifecycle.viewModel.ktx)
    implementation(libs.jake.timber)
    // TODO: think about removing and use the api from shared
    implementation(libs.dropbox.core)
    implementation(libs.androidx.biometric.ktx)

    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    androidTestImplementation(libs.androidx.compose.ui.ui.test)
}