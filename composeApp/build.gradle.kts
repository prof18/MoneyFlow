@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.sqldelight)
}

val appVersionCode: String by project
val appVersionName: String by project
val javaVersion: JavaVersion by rootProject.extra

kotlin {
    jvmToolchain(21)

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.add("-opt-in=androidx.compose.ui.ExperimentalComposeUiApi")
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts += listOf("-lsqlite3")
        }
    }

    sourceSets {
        applyDefaultHierarchyTemplate()
        sourceSets.all {
            languageSettings.optIn("kotlin.RequiresOptIn")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            languageSettings.optIn("com.russhwolf.settings.ExperimentalSettingsImplementation")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCRefinement")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.components.resources)

                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.kotlinx.coroutine.core)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.russhwolf.multiplatform.settings)
                implementation(libs.touchlab.kermit)
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutine.extensions)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.koin.test)
                implementation(libs.kotlinx.coroutine.test)
                implementation(libs.cashapp.turbine)
                implementation(libs.russhwolf.multiplatform.settings.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.lifecycle.viewmodel.ktx)
                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation(libs.androidx.navigation.compose)
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
                implementation(libs.jake.timber)
                implementation(libs.androidx.biometric.ktx)
                implementation(libs.sqldelight.android.driver)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlin.test.junit)
                implementation(libs.bundles.androidx.test)
                implementation(libs.kotlinx.coroutine.test)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutine.core)
                implementation(libs.sqldelight.native.driver)
            }
        }
        val iosTest by getting
    }
}

android {
    namespace = "com.prof18.moneyflow"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.prof18.moneyflow"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = appVersionCode.toInt()
        versionName = appVersionName
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    buildFeatures { compose = true }
}

dependencies {
    debugImplementation(compose.uiTooling)
    androidTestImplementation(libs.compose.ui.test)
}

sqldelight {
    databases {
        create("MoneyFlowDB") {
            packageName.set("com.prof18.moneyflow.db")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/com/prof18/moneyflow/schema"))
        }
    }
}
