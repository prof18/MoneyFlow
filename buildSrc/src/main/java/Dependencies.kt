object Versions {
    const val kotlin = "1.4.20"
    const val koin = "3.0.0-alpha-4"
    const val ktx = "1.0.1"
    const val lifecycle = "2.2.0-alpha01"
    const val compose = "1.0.0-alpha07"
    const val junit = "4.12"
    const val coroutinesMt = "1.4.1-native-mt"
    const val sqlDelight = "1.4.4"
    const val stately = "1.1.0"
    const val navComposeVersion = "1.0.0-alpha02"
    const val kotlinDateTime = "0.1.0"
    const val androidXTest = "1.3.0"
    const val androidXTestExt = "1.1.2"
    const val turbine = "0.3.0"
}

object Deps {

    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val stately = "co.touchlab:stately-common:${Versions.stately}"
    const val kotlinDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinDateTime}"
    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"

    object Koin {
        const val compose = "org.koin:koin-androidx-compose:${Versions.koin}"
        const val core = "org.koin:koin-core:${Versions.koin}"
        const val coreMultiplatform = "org.koin:koin-core:${Versions.koin}"
        const val androidViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        const val test = "org.koin:koin-test:${Versions.koin}"
    }

    object Compose {

        const val core = "androidx.compose.ui:ui:${Versions.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val materialIconsExtended =
            "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val tooling = "androidx.ui:ui-tooling:${Versions.compose}"

        //        const val test = "androidx.compose.test:test-core:${Versions.compose}"
        const val uiTest = "androidx.ui:ui-test:${Versions.compose}"
        const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val composeNavigation =
            "androidx.navigation:navigation-compose:${Versions.navComposeVersion}"
    }

    object KotlinTest {
        const val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        const val annotations =
            "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
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
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesMt}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesMt}"
    }

    object SqlDelight {
        const val gradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        const val coroutineExtensions = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        const val driver = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
        const val runtimeJdk = "com.squareup.sqldelight:runtime-jvm:${Versions.sqlDelight}"
        const val driverIos = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        const val driverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        const val driverMacOs =
            "com.squareup.sqldelight:native-driver-macosx64:${Versions.sqlDelight}"
        const val runtimeMacOs = "com.squareup.sqldelight:runtime-macosx64:${Versions.sqlDelight}"
    }

}


//object Koin {
//    val core = "org.koin:koin-core:${Versions.koin}"
//    val android = "org.koin:koin-android:${Versions.koin}"
//    val androidViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
//}


