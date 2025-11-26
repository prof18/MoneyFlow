import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_TEST_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_TEST_SRC_DIR_KOTLIN

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.triplet.play) apply false
}

val javaVersion by extra { JavaVersion.VERSION_21 }

allprojects {
    apply {
        plugin(rootProject.libs.plugins.detekt.get().pluginId)
    }

    dependencies {
        detektPlugins(rootProject.libs.io.gitlab.arturbosch.detekt.formatting) {
            exclude(group = "org.slf4j", module = "slf4j-nop")
        }
    }

    detekt {
        source = files(
            "src",
            DEFAULT_SRC_DIR_JAVA,
            DEFAULT_TEST_SRC_DIR_JAVA,
            DEFAULT_SRC_DIR_KOTLIN,
            DEFAULT_TEST_SRC_DIR_KOTLIN,
        )
        toolVersion = rootProject.libs.versions.detekt.get()
        config = rootProject.files("config/detekt/detekt.yml")
        parallel = true
    }
}
