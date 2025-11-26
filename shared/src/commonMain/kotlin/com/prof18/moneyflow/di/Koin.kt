package com.prof18.moneyflow.di

import com.prof18.moneyflow.MainViewModel
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.data.SettingsRepository
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.features.addtransaction.AddTransactionViewModel
import com.prof18.moneyflow.features.alltransactions.AllTransactionsViewModel
import com.prof18.moneyflow.features.categories.CategoriesViewModel
import com.prof18.moneyflow.features.home.HomeViewModel
import com.prof18.moneyflow.features.settings.SettingsViewModel
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.utils.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
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

    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AddTransactionViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::AllTransactionsViewModel)
}

expect val platformModule: Module
