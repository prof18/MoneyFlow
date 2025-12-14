package com.prof18.moneyflow.di

import com.prof18.moneyflow.IosBiometricAvailabilityChecker
import com.prof18.moneyflow.database.createDatabaseDriver
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

public fun initKoinIos(): KoinApplication = initKoin(
    additionalModules = emptyList(),
)

public fun doInitKoinIos(): KoinApplication = initKoinIos()

internal actual val platformModule: Module = module {
    single<Settings> { NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults) }
    single { createDatabaseDriver(useDebugDatabaseName = false) }
    single<BiometricAvailabilityChecker> { IosBiometricAvailabilityChecker() }
}
