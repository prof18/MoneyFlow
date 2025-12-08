import java.net.URI

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
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.kmp.detekt)
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
            baseName = "MoneyFlowKit"
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
                api(libs.compose.runtime)
                api(libs.compose.foundation)
                api(libs.compose.foundation)
                api(libs.compose.material3)
                implementation(libs.compose.material.icons.extended)
                api(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation(libs.jetbrains.ui.tooling.preview)

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
                implementation(libs.immutable.collections)
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
                implementation(libs.roborazzi)
                implementation(libs.roborazziJunitRule)
                implementation(libs.roborazzi.compose)
                implementation(libs.compose.ui.test)
                implementation(libs.robolectric)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutine.core)
                implementation(libs.sqldelight.native.driver)
            }
        }
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

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    buildFeatures { compose = true }
}

dependencies {
    debugImplementation(libs.jetbrains.ui.tooling.preview)
    androidTestImplementation(libs.compose.ui.test)
}

tasks.withType<Test>().configureEach {
    systemProperty("roborazzi.test.record.dir", rootProject.layout.projectDirectory.dir("image/roborazzi").asFile.path)

    listOf(
        "http" to System.getenv("http_proxy"),
        "https" to System.getenv("https_proxy"),
    ).forEach { (scheme, proxyValue) ->
        proxyValue
            ?.takeIf { it.isNotBlank() }
            ?.let(::URI)
            ?.let { proxyUri ->
                proxyUri.host?.let { host ->
                    systemProperty("$scheme.proxyHost", host)
                }
                proxyUri.port.takeIf { it != -1 }?.let { port ->
                    systemProperty("$scheme.proxyPort", port)
                }
            }
    }
}

sqldelight {
    databases {
        create("MoneyFlowDB") {
            packageName.set("com.prof18.moneyflow.db")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/com/prof18/moneyflow/schema"))
        }
    }
}
