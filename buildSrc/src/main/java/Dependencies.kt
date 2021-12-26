object Versions {
    const val kotlin = "1.6.0"
    const val agp = "7.0.4"
    const val koin = "3.1.4"
    const val coreKTX = "1.7.0-rc01"
    const val compose = "1.1.0-rc01"
    const val junit = "4.12"
    const val coroutinesMt = "1.6.0-native-mt"
    const val coroutines = "1.6.0"
    const val sqlDelight = "1.5.3"
    const val stately = "1.2.0"
    const val navComposeVersion = "2.4.0-beta02"
    const val activityComposeVersion = "1.4.0"
    const val kotlinDateTime = "0.3.1"
    const val androidXTest = "1.4.0"
    const val androidXTestExt = "1.1.2"
    const val turbine = "0.7.0"
    const val timber = "5.0.1"
    const val dropboxCore = "5.0.0"
    const val multiplatformSettings = "0.8.1"
    const val androidCrypto = "1.1.0-alpha03"
    const val viewModelKTX = "2.4.0"
    const val biometric = "1.2.0-alpha04"
    const val pagingCompose = "1.0.0-alpha14"
    const val gradleVersions = "0.39.0"
}

object Deps {
    const val agp = "com.android.tools.build:gradle:${Versions.agp}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val coreKTX = "androidx.core:core-ktx:${Versions.coreKTX}"

    const val stately = "co.touchlab:stately-common:${Versions.stately}"
    const val kotlinDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinDateTime}"
    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val dropboxCore = "com.dropbox.core:dropbox-core-sdk:${Versions.dropboxCore}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val multiplatformSettings = "com.russhwolf:multiplatform-settings:${Versions.multiplatformSettings}"
    const val multiplatformSettingsTest = "com.russhwolf:multiplatform-settings-test:${Versions.multiplatformSettings}"
    const val androidCrypto = "androidx.security:security-crypto:${Versions.androidCrypto}"
    const val viewModelKTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKTX}"
    const val biometric = "androidx.biometric:biometric-ktx:${Versions.biometric}"
    const val gradleVersionPlugin = "com.github.ben-manes.versions"
    const val gradleVersion = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersions}"


    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
    }

    object Compose {
        const val core = "androidx.compose.ui:ui:${Versions.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val materialIconsCore = "androidx.compose.material:material-icons-core:${Versions.compose}"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val uiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navComposeVersion}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.activityComposeVersion}"
        const val paging = "androidx.paging:paging-compose:${Versions.pagingCompose}"
    }

    object KotlinTest {
        const val common = "test-common"
        const val annotations = "test-annotations-common"
        const val jvm = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        const val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    }

    object AndroidXTest {
        const val core = "androidx.test:core:${Versions.androidXTest}"
        const val junit = "androidx.test.ext:junit:${Versions.androidXTestExt}"
        const val runner = "androidx.test:runner:${Versions.androidXTest}"
        const val rules = "androidx.test:rules:${Versions.androidXTest}"
    }

    object Coroutines {
        const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesMt}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesMt}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object SqlDelight {
        const val gradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        const val coroutineExtensions = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        const val driver = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
        const val runtimeJdk = "com.squareup.sqldelight:runtime-jvm:${Versions.sqlDelight}"
        const val driverIos = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        const val driverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
    }
}



