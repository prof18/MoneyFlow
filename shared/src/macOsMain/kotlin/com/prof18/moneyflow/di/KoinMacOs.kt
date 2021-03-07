package com.prof18.moneyflow.di

import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

fun initKoinMacos(
//    userDefaults: NSUserDefaults,
    doOnStartup: () -> Unit
): KoinApplication = initKoin(
    module {
//        single<Settings> { AppleSettings(userDefaults) }
//        single { appInfo }
        single { doOnStartup }
    }
)

actual val platformModule = module {

    // TODO: add platform specific stuff

//    single<SqlDriver> { NativeSqliteDriver(KaMPKitDb.Schema, "KampkitDb") }

}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, null) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier, null)
}

