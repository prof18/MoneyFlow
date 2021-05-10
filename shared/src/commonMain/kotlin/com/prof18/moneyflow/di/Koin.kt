package com.prof18.moneyflow.di

import com.prof18.moneyflow.data.DropboxSyncRepositoryImpl
import com.prof18.moneyflow.data.MoneyRepositoryImpl
import com.prof18.moneyflow.data.db.DatabaseSource
import com.prof18.moneyflow.data.db.DatabaseSourceImpl
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.repository.DropboxSyncRepository
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCase
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCaseImpl
import com.prof18.moneyflow.presentation.categories.CategoriesUseCase
import com.prof18.moneyflow.presentation.categories.CategoriesUseCaseImpl
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUseCaseImpl
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUserCase
import com.prof18.moneyflow.presentation.home.HomeUseCase
import com.prof18.moneyflow.presentation.home.HomeUseCaseImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(platformModule, coreModule)
    }
}

private val coreModule = module {

    single<DatabaseSource> { DatabaseSourceImpl(get(), Dispatchers.Main) }
    single { SettingsSource(get()) }

    // Repository
    single<DropboxSyncRepository> { DropboxSyncRepositoryImpl(get()) }
    single<MoneyRepository> { MoneyRepositoryImpl(get()) }

    // Use Cases
    factory<HomeUseCase> { HomeUseCaseImpl(get()) }
    factory<AddTransactionUseCase> { AddTransactionUseCaseImpl(get()) }
    factory<CategoriesUseCase> { CategoriesUseCaseImpl(get()) }
    factory<DropboxSyncUserCase> { DropboxSyncUseCaseImpl(get()) }
}

expect val platformModule: Module
