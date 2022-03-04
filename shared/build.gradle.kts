import org.jetbrains.kotlin.konan.target.KonanTarget
import org.jmailen.gradle.kotlinter.tasks.LintTask

@Suppress("DSL_SCOPE_VIOLATION") // because of https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.native.cocoapods)
    alias(libs.plugins.android.library)
    id("com.squareup.sqldelight")
    id("co.touchlab.kermit")
}

val sharedLibGroup: String by project
val sharedLibVersion: String by project
val javaVersion: JavaVersion by rootProject.extra

group = sharedLibGroup
version = sharedLibVersion

tasks {
    named<LintTask>("lintKotlinCommonMain") {
        exclude("com/prof18/moneyflow/db/**/*.kt")
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = libs.versions.java.get()
        }
    }
}

kotlin {
    android()
    ios() {

        binaries {
            getTest("DEBUG").apply {
                val frameworkPath =
                    "$rootDir/dropbox-api/build/cocoapods/synthetic/IOS/dropbox_api/build/Release-iphonesimulator/ObjectiveDropboxOfficial"
                linkerOpts("-F$frameworkPath")
                linkerOpts("-rpath", frameworkPath)
                linkerOpts("-framework", "ObjectiveDropboxOfficial")
            }
        }
    }

    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Kotlin Multiplatform Library for MoneyFlow"
        homepage = "https://github.com/prof18/MoneyFlow"
        authors = "Marco Gomiero"
        license = "APACHE"
        ios.deploymentTarget = "15"
        podfile = project.file("../iosApp/Podfile")

        framework {
            isStatic = false
            export(project(":dropbox-api"))
            transitiveExport = true
            linkerOpts.add("-lsqlite3")

            val isSimulator =
                this.target.konanTarget == KonanTarget.IOS_X64 || this.target.konanTarget == KonanTarget.IOS_SIMULATOR_ARM64
            val frameworkPath = if (isSimulator) {
                "$rootDir/dropbox-api/build/cocoapods/synthetic/IOS/dropbox_api/build/Release-iphonesimulator/ObjectiveDropboxOfficial"
            } else {
                "$rootDir/dropbox-api/build/cocoapods/synthetic/IOS/dropbox_api/build/Release-iphoneos/ObjectiveDropboxOfficial"
            }
            linkerOpts("-F$frameworkPath")
            linkerOpts("-rpath", frameworkPath)
            linkerOpts("-framework", "ObjectiveDropboxOfficial")
        }
    }

    sourceSets {

        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("com.russhwolf.settings.ExperimentalSettingsImplementation")
            }
        }

        val commonMain by getting {
            dependencies {
                api(project(":dropbox-api"))
                implementation(libs.squareup.sqldelight.runtime)
                implementation(libs.squareup.sqldelight.coroutineExtensions)
                implementation(libs.kotlinx.coroutine.core)
                implementation(libs.touchlab.stately)
                implementation(libs.koin.koinCore)
                implementation(libs.kotlinx.datetime)
                implementation(libs.russhwolf.multiplatform.settings)
                implementation(libs.touchlab.kermit)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.koin.koinTest)
                implementation(libs.kotlinx.coroutine.test)
                implementation(libs.cashapp.turbine)
                implementation(libs.russhwolf.multiplatform.settings.test)

            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.security.crypto)
                implementation(libs.squareup.sqldelight.androidDriver)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(libs.kotlin.kotlinTest)
                implementation(libs.kotlin.kotlinTestJunit)
                implementation(libs.bundles.androidx.test)
                implementation(libs.kotlinx.coroutine.test)
                implementation(libs.squareup.sqldelight.sqliteDriver)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.squareup.sqldelight.nativeDriver)
                implementation(libs.kotlinx.coroutine.core)
            }
        }
        val iosTest by getting {
            dependencies {
                implementation(libs.squareup.sqldelight.nativeDriver)
            }
        }
    }

    sourceSets.matching {
        it.name.endsWith("Test")
    }.configureEach {
        languageSettings.optIn("kotlin.time.ExperimentalTime")
        languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
    }
}

sqldelight {
    database("MoneyFlowDB") {
        packageName = "com.prof18.moneyflow.db"
        schemaOutputDirectory = file("src/main/sqldelight/databases")
    }
}

val releaseBuild: String by project
kermit {
    if (releaseBuild.toBoolean()) {
        stripBelow = co.touchlab.kermit.gradle.StripSeverity.Info
    }
}