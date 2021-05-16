object Versions {
    const val kotlin = "1.5.0"
    const val koin = "3.0.1"
    const val ktx = "1.0.1"
    const val lifecycle = "2.2.0-alpha01"
    const val compose = "1.0.0-beta06"
    const val junit = "4.12"
    const val coroutinesMt = "1.4.3-native-mt"
    const val coroutines = "1.4.2"
    const val sqlDelight = "1.5.0"
    const val stately = "1.1.7"
    const val navComposeVersion = "1.0.0-alpha10"
    const val activityComposeVersion = "1.3.0-alpha07"
    const val kotlinDateTime = "0.2.0"
    const val androidXTest = "1.3.0"
    const val androidXTestExt = "1.1.2"
    const val turbine = "0.5.0"
    const val timber = "4.7.1"
    const val activityKTX = "1.1.0"
    const val dropboxCore = "4.0.0"
    const val multiplatformSettings = "0.7.4"
    const val androidCrypto = "1.1.0-alpha02"
    const val viewModelKTX = "2.3.1"
}

object Deps {

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
        const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val uiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navComposeVersion}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.activityComposeVersion}"
    }

    object KotlinTest {
        const val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        const val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
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
        const val driverMacOs = "com.squareup.sqldelight:native-driver-macosx64:${Versions.sqlDelight}"
        const val runtimeMacOs = "com.squareup.sqldelight:runtime-macosx64:${Versions.sqlDelight}"
    }

}



