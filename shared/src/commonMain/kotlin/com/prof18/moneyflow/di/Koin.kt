package com.prof18.moneyflow.di

import com.prof18.moneyflow.data.MoneyRepositoryImpl
import com.prof18.moneyflow.data.SettingsRepositoryImpl
import com.prof18.moneyflow.data.db.DatabaseSource
import com.prof18.moneyflow.data.db.DatabaseSourceImpl
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.domain.repository.SettingsRepository
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCase
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsUseCase
import com.prof18.moneyflow.presentation.categories.CategoriesUseCase
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUseCase
import com.prof18.moneyflow.presentation.home.HomeUseCase
import com.prof18.moneyflow.presentation.main.MainUseCase
import com.prof18.moneyflow.presentation.settings.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
   return  startKoin {
        appDeclaration()
        modules(platformModule, coreModule)
    }
}

private val coreModule = module {

    single<DatabaseSource> { DatabaseSourceImpl(get(), Dispatchers.Default) }
    single { SettingsSource(get()) }

    // Repository
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<MoneyRepository> { MoneyRepositoryImpl(get()) }

    // Use Cases
    factory { MainUseCase(get()) }
    factory { HomeUseCase(get(), get()) }
    factory { AddTransactionUseCase(get()) }
    factory { SettingsUseCase(get()) }
    factory { AllTransactionsUseCase(get()) }
    factory { CategoriesUseCase(get()) }
    factory { DropboxSyncUseCase(get()) }
}

expect val platformModule: Module
