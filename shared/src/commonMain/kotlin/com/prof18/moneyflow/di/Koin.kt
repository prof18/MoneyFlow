package com.prof18.moneyflow.di

import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.data.SettingsRepository
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.utils.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(additionalModules: List<Module>): KoinApplication {
    return startKoin {
        modules(additionalModules + platformModule + coreModule)
    }
}

private val coreModule = module {

    single { DatabaseHelper(get(), Dispatchers.Default) }
    single { SettingsSource(get()) }
    single { MoneyFlowErrorMapper() }

    factory { DispatcherProvider() }

    // Repository
    single { SettingsRepository(get()) }
    single { MoneyRepository(get()) }
}

expect val platformModule: Module
