package com.prof18.moneyflow.di

import com.prof18.moneyflow.MainViewModel
import com.prof18.moneyflow.features.addtransaction.AddTransactionViewModel
import com.prof18.moneyflow.features.alltransactions.AllTransactionsViewModel
import com.prof18.moneyflow.features.categories.CategoriesViewModel
import com.prof18.moneyflow.features.home.HomeViewModel
import com.prof18.moneyflow.features.settings.DropboxClient
import com.prof18.moneyflow.features.settings.DropboxLoginViewModel
import com.prof18.moneyflow.features.settings.SettingsViewModel
import com.prof18.moneyflow.utils.DatabaseImportExport
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DatabaseImportExport(get()) }
    single { DropboxClient(get(), get()) }
    
    // View Models
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get())}
    viewModel { AddTransactionViewModel(get(), get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { DropboxLoginViewModel(get(), get()) }
    viewModel { AllTransactionsViewModel(get(), get()) }
}