package com.prof18.moneyflow.di

import com.prof18.moneyflow.data.db.DatabaseSource
import com.prof18.moneyflow.database.DBImportExport
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.features.addtransaction.AddTransactionViewModel
import com.prof18.moneyflow.features.alltransactions.AllTransactionsViewModel
import com.prof18.moneyflow.features.categories.CategoriesViewModel
import com.prof18.moneyflow.features.home.HomeViewModel
import com.prof18.moneyflow.features.settings.SettingsViewModel
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCase
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsUseCase
import com.prof18.moneyflow.presentation.categories.CategoriesUseCase
import com.prof18.moneyflow.presentation.home.HomeUseCase
import com.prof18.moneyflow.presentation.main.MainUseCase
import com.prof18.moneyflow.presentation.settings.SettingsUseCase
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
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

    fun homeUseCase(): HomeUseCase = koin.get()
    fun categoriesUseCase(): CategoriesUseCase = koin.get()
    fun addTransactionUseCase(): AddTransactionUseCase = koin.get()
    fun allTransactionsUseCase(): AllTransactionsUseCase = koin.get()
    fun settingsUseCase(): SettingsUseCase = koin.get()
    fun mainUseCase(): MainUseCase = koin.get()
    fun errorMapper(): MoneyFlowErrorMapper = koin.get()

    // Shared ViewModels (ready for iOS usage when UI migrates)
    fun homeViewModel(): HomeViewModel = koin.get()
    fun categoriesViewModel(): CategoriesViewModel = koin.get()
    fun addTransactionViewModel(): AddTransactionViewModel = koin.get()
    fun allTransactionsViewModel(): AllTransactionsViewModel = koin.get()
    fun settingsViewModel(): SettingsViewModel = koin.get()
}

// Backwards compatibility for Swift call site naming
fun initKoinIos(): KoinApplication = KoinIosDependencies.start()

actual val platformModule = module {
    single<Settings> { KeychainSettings(service = "MoneyFlow") }
    factory { DBImportExport() }
    single {
        DatabaseHelper.setupDatabase()
        DatabaseHelper.instance
    }

    single { DatabaseSource(get(), Dispatchers.Main) }
    single { MoneyRepository(get()) }

    // Use Cases
    factory { MainUseCase(get()) }
    factory { HomeUseCase(get(), get(), get()) }
    factory { AddTransactionUseCase(get(), get()) }
    factory { SettingsUseCase(get()) }
    factory { AllTransactionsUseCase(get()) }
    factory { CategoriesUseCase(get()) }

    // Shared ViewModels
    factory { HomeViewModel(get(), get(), get()) }
    factory { AddTransactionViewModel(get(), get(), get()) }
    factory { SettingsViewModel(get(), get()) }
    factory { AllTransactionsViewModel(get(), get()) }
    factory { CategoriesViewModel(get(), get()) }
}

fun Koin.get(objCClass: ObjCClass): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz)
}
