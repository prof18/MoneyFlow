import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_TEST_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_TEST_SRC_DIR_KOTLIN
import java.io.ByteArrayOutputStream

@Suppress("DSL_SCOPE_VIOLATION") // because of https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.versionsBenManes)
    alias(libs.plugins.versionCatalogUpdate)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.native.cocoapods) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.detekt)
}

val javaVersion by extra { JavaVersion.VERSION_11 }
val sharedLibGroup: String by project
val sharedLibVersion: String by project

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.squareup.sqldelight.gradlePlugin)
        classpath(libs.touchlab.kermit.plugin)
    }
}

//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        gradlePluginPortal()
//        maven(url = "https://kotlin.bintray.com/kotlinx")
//        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
//    }
//}

tasks {
//    val swiftLint by registering {
//        ByteArrayOutputStream().use { outputStream ->
//            exec {
//                workingDir = File("${rootDir.path}/iosApp")
//                commandLine("swiftlint")
//                standardOutput = outputStream
//            }
//            val output = outputStream.toString()
//            println(output)
//        }
//    }
}


subprojects {

    apply(plugin = rootProject.libs.plugins.kotlinter.get().pluginId)
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)


    detekt {
        source = files(
            "src",
            DEFAULT_SRC_DIR_JAVA,
            DEFAULT_TEST_SRC_DIR_JAVA,
            DEFAULT_SRC_DIR_KOTLIN,
            DEFAULT_TEST_SRC_DIR_KOTLIN,
        )
        toolVersion = rootProject.libs.versions.gradlePlugins.detekt.get()
        config = rootProject.files("config/detekt/detekt.yml")
        parallel = true
    }
}

group = sharedLibGroup
version = sharedLibVersion

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

// Kotlin DSL
tasks.withType<Detekt>().configureEach {
    jvmTarget = libs.versions.java.get()
}
tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = libs.versions.java.get()
}

tasks.withType<DependencyUpdatesTask> {
    resolutionStrategy {
        componentSelection {
            all {
                when {
                    isNonStable(candidate.version) && !isNonStable(currentVersion) -> {
                        reject("Updating stable to non stable is not allowed")
                    }
                    candidate.module == "kotlin-gradle-plugin" && candidate.version != libs.versions.kotlin.get() -> {
                        reject("Keep Kotlin version on the version specified in libs.versions.toml")
                    }
                }
            }
        }
    }
}

versionCatalogUpdate {
    keep {
        versions.add("android-compileSdk")
        versions.add("android-minSdk")
        versions.add("android-targetSdk")
        versions.add("java")
    }
}
