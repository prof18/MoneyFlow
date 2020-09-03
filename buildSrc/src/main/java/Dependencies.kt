object Versions {
    const val kotlin = "1.4.0"
    const val kotlinCoroutines = "1.3.9-native-mt"
    const val koin = "3.0.1-alpha-2"
    const val ktx = "1.0.1"
    const val lifecycle = "2.2.0-alpha01"
    const val compose = "1.0.0-alpha02"
    const val junit = "4.12"
    const val coroutines = "1.3.9-native-mt"
    const val sqlDelight = "1.4.1"
    const val stately = "1.1.0"
}

object Deps {

    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val koinCore = "org.koin:koin-core:${Versions.koin}"
    const val koinTest = "org.koin:koin-test:${Versions.koin}"

    const val stately = "co.touchlab:stately-common:${Versions.stately}"

    object Compose {

        const val core = "androidx.compose.ui:ui:${Versions.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val tooling = "androidx.ui:ui-tooling:${Versions.compose}"
        const val test = "androidx.compose.test:test-core:${Versions.compose}"
        const val uiTest = "androidx.ui:ui-test:${Versions.compose}"
        const val runtimeLiveData =  "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    }

    object KotlinTest {
        const val common =      "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        const val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
        const val jvm =         "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        const val junit =       "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    }
    object Coroutines {
        const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }
    object SqlDelight{
        const val gradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        const val runtimeJdk = "com.squareup.sqldelight:runtime-jvm:${Versions.sqlDelight}"
        const val driverIos = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        const val driverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        const val driverMacOs = "com.squareup.sqldelight:native-driver-macosx64:${Versions.sqlDelight}"
        const val runtimeMacOs = "com.squareup.sqldelight:runtime-macosx64:${Versions.sqlDelight}"
    }

}



//object Koin {
//    val core = "org.koin:koin-core:${Versions.koin}"
//    val android = "org.koin:koin-android:${Versions.koin}"
//    val androidViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
//}


