package com.prof18.moneyflow.di

import com.prof18.moneyflow.data.DropboxSyncRepository
import com.prof18.moneyflow.data.MoneyRepositoryImpl
import com.prof18.moneyflow.data.SettingsRepositoryImpl
import com.prof18.moneyflow.data.db.DatabaseSource
import com.prof18.moneyflow.data.db.DatabaseSourceImpl
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.domain.repository.SettingsRepository
import com.prof18.moneyflow.platform.LocalizedStringProvider
import com.prof18.moneyflow.platform.LocalizedStringProviderImpl
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCase
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsUseCase
import com.prof18.moneyflow.presentation.categories.CategoriesUseCase
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUseCase
import com.prof18.moneyflow.presentation.home.HomeUseCase
import com.prof18.moneyflow.presentation.main.MainUseCase
import com.prof18.moneyflow.presentation.settings.SettingsUseCase
import com.prof18.moneyflow.utils.DispatcherProvider
import com.prof18.moneyflow.utils.DispatcherProviderImpl
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

    single<DatabaseSource> { DatabaseSourceImpl(get(), Dispatchers.Default) }
    single { SettingsSource(get()) }
    single<LocalizedStringProvider> { LocalizedStringProviderImpl() }
    single { MoneyFlowErrorMapper(get()) }

    factory<DispatcherProvider> { DispatcherProviderImpl() }

    // Repository
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<MoneyRepository> { MoneyRepositoryImpl(get()) }
    single { DropboxSyncRepository(get(), get(), get(), get()) }

    // Use Cases
    factory { MainUseCase(get()) }
    factory { HomeUseCase(get(), get(), get()) }
    factory { AddTransactionUseCase(get(), get()) }
    factory { SettingsUseCase(get()) }
    factory { AllTransactionsUseCase(get()) }
    factory { CategoriesUseCase(get()) }
    factory {
        DropboxSyncUseCase(
            dropboxSyncRepository = get(),
            localizedStringProvider = get(),
        )
    }
}

expect val platformModule: Module
