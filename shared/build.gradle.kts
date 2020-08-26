import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-android-extensions")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.squareup.sqldelight")
}
group = "com.prof18"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
kotlin {
    ios()

    macosX64("macOS")
    android()


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
                implementation(Deps.Coroutines.common)
                implementation(Deps.stately)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.2.0")
                implementation(Deps.SqlDelight.driverAndroid)
            }
        }
        val androidTest by getting
        val iosMain by getting {
            dependencies {
                implementation(Deps.SqlDelight.driverIos)
            }
        }
        val iosTest by getting
        val macOSMain by getting {
            dependencies {
                implementation(Deps.SqlDelight.driverMacOs)
                implementation(Deps.SqlDelight.runtimeMacOs)
            }
        }
    }
}
android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

sqldelight {
    database("MoneyFlowDB") {
        packageName = "com.prof18.moneyflow.db"
        schemaOutputDirectory = file("src/main/sqldelight/databases")
    }
}

//val packForXcode by tasks.creating(Sync::class) {
//    group = "build"
//    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
//    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)
//    inputs.property("mode", mode)
//    dependsOn(framework.linkTask)
//    val targetDir = File(buildDir, "xcode-frameworks")
//    from({ framework.outputDirectory })
//    into(targetDir)
//}
//tasks.getByName("build").dependsOn(packForXcode)
//dependencies {
//    implementation(kotlin("stdlib-jdk8"))
//}
//val compileKotlin: KotlinCompile by tasks
//compileKotlin.kotlinOptions {
//    jvmTarget = "1.8"
//}
//val compileTestKotlin: KotlinCompile by tasks
//compileTestKotlin.kotlinOptions {
//    jvmTarget = "1.8"
//}