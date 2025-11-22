package com.prof18.moneyflow.di

import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.data.SettingsRepository
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.platform.LocalizedStringProvider
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCase
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsUseCase
import com.prof18.moneyflow.presentation.categories.CategoriesUseCase
import com.prof18.moneyflow.presentation.home.HomeUseCase
import com.prof18.moneyflow.presentation.main.MainUseCase
import com.prof18.moneyflow.presentation.settings.SettingsUseCase
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
    single { LocalizedStringProvider() }
    single { MoneyFlowErrorMapper(get()) }

    factory { DispatcherProvider() }

    // Repository
    single { SettingsRepository(get()) }
    single { MoneyRepository(get()) }
    

    // Use Cases
    factory { MainUseCase(get()) }
    factory { HomeUseCase(get(), get(), get()) }
    factory { AddTransactionUseCase(get(), get()) }
    factory { SettingsUseCase(get()) }
    factory { AllTransactionsUseCase(get()) }
    factory { CategoriesUseCase(get()) }
}

expect val platformModule: Module
