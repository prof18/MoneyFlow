import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import com.android.build.gradle.internal.lint.AndroidLintTask

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

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    }
}


subprojects {

    apply(plugin = rootProject.libs.plugins.kotlinter.get().pluginId)
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)


    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
    }



//    detekt {
//        source = files("src/main/java", "src/main/kotlin")
//        config = rootProject.files("build-config/detekt.yml")
//        buildUponDefaultConfig = true
//    }

    afterEvaluate {

//        detekt {
//            source = files("src/main/java", "src/main/kotlin")
//            config = rootProject.files("build-config/detekt.yml")
//            buildUponDefaultConfig = true
//        }

//        tasks {
//
//
//            withType<Detekt> {
//                // Required for type resolution
//                jvmTarget = "1.8"
//                config.setValue(files("$rootDir/config/detekt/detekt-config.yml"))
////                config = files("$rootDir/build-config/detekt-config.yml")
////                reports {
////                    sarif {
////                        required.set(true)
////                    }
////                }
//            }
//        }
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
