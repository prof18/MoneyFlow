plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.squareup.sqldelight")
}
group = "com.prof18"
version = "1.0-SNAPSHOT"

// Workaround to solve https://youtrack.jetbrains.com/issue/KT-43944
android {
    compileSdk= 30
    defaultConfig {
        minSdk = 24
        targetSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

kotlin.sourceSets.matching {
    it.name.endsWith("Test")
}.configureEach {
    languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
    languageSettings.useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
}

kotlin {

    android()

    ios()

    macosX64("macOS")

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework> {
            isStatic = false
        }
    }

    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
    }

    sourceSets {

        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(Deps.SqlDelight.runtime)
                implementation(Deps.SqlDelight.coroutineExtensions)
                implementation(Deps.Coroutines.common)
                implementation(Deps.stately)
                implementation(Deps.Koin.core)
                implementation(Deps.kotlinDateTime)
                implementation(Deps.multiplatformSettings)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(Deps.Koin.test)
                implementation(Deps.turbine)
                implementation(Deps.multiplatformSettingsTest)

            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.2.0")
                implementation(Deps.androidCrypto)
                implementation(Deps.SqlDelight.driverAndroid)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(Deps.SqlDelight.driver)

                implementation(Deps.KotlinTest.jvm)
                implementation(Deps.KotlinTest.junit)
                implementation(Deps.AndroidXTest.core)
                implementation(Deps.AndroidXTest.junit)
                implementation(Deps.AndroidXTest.runner)
                implementation(Deps.AndroidXTest.rules)
                implementation(Deps.Coroutines.test)

            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Deps.SqlDelight.driverIos)
                implementation(Deps.Coroutines.common) {
                    version {
                        strictly(Versions.coroutinesMt)
                    }
                }
            }
        }
        val iosTest by getting {
            dependencies {
                implementation(Deps.SqlDelight.driverIos)
            }
        }
        val macOSMain by getting {
            dependencies {
                implementation(Deps.SqlDelight.driverMacOs)
                implementation(Deps.SqlDelight.runtimeMacOs)
            }
        }
        val macOSTest by getting {

        }
    }
}

kotlin.sourceSets.matching {
    it.name.endsWith("Test")
}.configureEach {
    languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
    languageSettings.useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
}


sqldelight {
    database("MoneyFlowDB") {
        packageName = "com.prof18.moneyflow.db"
        schemaOutputDirectory = file("src/main/sqldelight/databases")
    }
}