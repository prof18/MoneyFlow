import org.jetbrains.kotlin.konan.target.KonanTarget;

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("co.touchlab.kermit")
}
group = Config.Release.sharedLibGroup
version = Config.Release.sharedLibVersion

android {
    compileSdk = Config.Android.compileSdk
    defaultConfig {
        minSdk = Config.Android.minSdk
        targetSdk = Config.Android.targetSdk
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
        sourceCompatibility = Config.Java.javaVersion
        targetCompatibility = Config.Java.javaVersion
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = Config.Java.javaVersionNumber
        }
    }
}



kotlin {
//    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
//
//
//        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework> {
//            isStatic = false
//            export(project(":dropbox-api"))
//
//
//            val isSimulator = this.target.konanTarget == KonanTarget.IOS_X64 || this.target.konanTarget == KonanTarget.IOS_SIMULATOR_ARM64
//            println(">>> isSimulator?? $isSimulator")
//
////            this.
//            val frameworkPath = if (isSimulator) {
//                "$rootDir/dropbox-api/build/cocoapods/synthetic/IOS/dropbox_api/build/Release-iphonesimulator/ObjectiveDropboxOfficial"
//            } else {
//                "$rootDir/dropbox-api/build/cocoapods/synthetic/IOS/dropbox_api/build/Release-iphoneos/ObjectiveDropboxOfficial"
//            }
//            linkerOpts("-F$frameworkPath")
//            linkerOpts("-rpath", frameworkPath)
//            linkerOpts("-framework", "ObjectiveDropboxOfficial")
//
////            transitiveExport = true
//        }
//    }

    android()
    ios() {

        binaries {
//            framework {
//                export(project(":dropbox-api"))
//            }

//            getFramework("DEBUG").apply {
//                println(">>>>> GHErerrrr")
//                val frameworkPath = "$rootDir/dropbox-api/build/cocoapods/synthetic/IOS/dropbox_api/build/Release-iphonesimulator/ObjectiveDropboxOfficial"
//                linkerOpts("-F$frameworkPath")
//                linkerOpts("-rpath", frameworkPath)
//                linkerOpts("-framework", "ObjectiveDropboxOfficial")
//            }

//            getTest("DEBUG").apply {
//                val frameworkPath =
//                    "$rootDir/dropbox-api/build/cocoapods/synthetic/IOS/dropbox_api/build/Release-iphonesimulator/ObjectiveDropboxOfficial"
//                linkerOpts("-F$frameworkPath")
//                linkerOpts("-rpath", frameworkPath)
//                linkerOpts("-framework", "ObjectiveDropboxOfficial")
//            }
//            getFramework("DEBUG").apply {
//                val frameworkPath = "$rootDir/dropbox-api/build/cocoapods/synthetic/IOS/dropbox_api/build/Release-iphonesimulator/ObjectiveDropboxOfficial"
//                linkerOpts("-F$frameworkPath")
//                linkerOpts("-rpath", frameworkPath)
//                linkerOpts("-framework", "ObjectiveDropboxOfficial")
//            }
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


            val isSimulator = this.target.konanTarget == KonanTarget.IOS_X64 || this.target.konanTarget == KonanTarget.IOS_SIMULATOR_ARM64
            println(">>> isSimulator?? $isSimulator")

//            this.
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
                implementation(Deps.SqlDelight.runtime)
                implementation(Deps.SqlDelight.coroutineExtensions)
                implementation(Deps.Coroutines.core)
                implementation(Deps.stately)
                implementation(Deps.Koin.core)
                implementation(Deps.kotlinDateTime)
                implementation(Deps.multiplatformSettings)
                implementation(Deps.kermit)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin(Deps.KotlinTest.common))
                implementation(kotlin(Deps.KotlinTest.annotations))
                implementation(Deps.Koin.test)
                implementation(Deps.Coroutines.test)
                implementation(Deps.turbine)
                implementation(Deps.multiplatformSettingsTest)

            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Deps.coreKTX)
                implementation(Deps.androidCrypto)
                implementation(Deps.SqlDelight.driverAndroid)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin(Deps.KotlinTest.common))
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
                implementation(Deps.Coroutines.core) {
                    version {
                        strictly(Versions.coroutines)
                    }
                }
            }
        }
        val iosTest by getting {
            dependencies {
                implementation(Deps.SqlDelight.driverIos)
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