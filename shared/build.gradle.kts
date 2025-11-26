@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
}

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
            baseName = "shared"
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
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.ui)
                api(compose.components.resources)
                implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.10.0-beta01")

                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.koin.compose.viewmodel.navigation)
                implementation(libs.koin.compose)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.androidx.navigation3.ui)
                implementation(libs.androidx.lifecycle.viewmodel.navigation3)
                implementation(libs.kotlinx.coroutine.core)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.russhwolf.multiplatform.settings)
                api(libs.touchlab.kermit)
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
                api(libs.androidx.activity.compose)
                api(libs.androidx.lifecycle.viewmodel.ktx)
                api(libs.androidx.lifecycle.viewmodel.compose)
                api(libs.koin.android)
                api(libs.androidx.biometric.ktx)
                implementation(libs.sqldelight.android.driver)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlin.test.junit)
                implementation(libs.bundles.androidx.test)
                implementation(libs.kotlinx.coroutine.test)
                implementation(libs.sqldelight.sqlite.driver)
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
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
