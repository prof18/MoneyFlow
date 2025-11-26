package com.prof18.moneyflow.di

import com.prof18.moneyflow.IosBiometricAvailabilityChecker
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.database.createDatabaseDriver
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults
import org.koin.core.KoinApplication
import org.koin.dsl.module
import org.koin.core.module.Module
import org.koin.core.Koin
import org.koin.core.context.stopKoin


fun initKoinIos(): KoinApplication = initKoin(
       additionalModules = emptyList()
   )


actual val platformModule: Module = module {
    single<Settings> { NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults) }
    single { createDatabaseDriver(useDebugDatabaseName = false) }
    single<BiometricAvailabilityChecker> { IosBiometricAvailabilityChecker() }
}
