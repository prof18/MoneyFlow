package com.prof18.moneyflow.di

import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.database.createDatabaseDriver
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import com.prof18.moneyflow.IosBiometricAvailabilityChecker
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import kotlinx.coroutines.Dispatchers
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.dsl.module

object KoinIosDependencies {
    private lateinit var koinApplication: KoinApplication

    val koin: Koin
        get() = koinApplication.koin

    fun start(additionalModules: List<org.koin.core.module.Module> = emptyList()): KoinApplication {
        if (!this::koinApplication.isInitialized) {
            koinApplication = initKoin(additionalModules)
        }
        return koinApplication
    }

    fun reload(additionalModules: List<org.koin.core.module.Module> = emptyList()) {
        if (this::koinApplication.isInitialized) {
            stopKoin()
        }
        koinApplication = initKoin(additionalModules)
    }

    fun errorMapper(): MoneyFlowErrorMapper = koin.get()
}

// Backwards compatibility for Swift call site naming
fun initKoinIos(): KoinApplication = KoinIosDependencies.start()

actual val platformModule = module {
    single<Settings> { NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults) }
    single { createDatabaseDriver(useDebugDatabaseName = false) }
    single<BiometricAvailabilityChecker> { IosBiometricAvailabilityChecker() }
}


