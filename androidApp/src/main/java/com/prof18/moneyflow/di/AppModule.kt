package com.prof18.moneyflow.di

import com.prof18.moneyflow.MainViewModel
import com.prof18.moneyflow.features.addtransaction.AddTransactionViewModel
import com.prof18.moneyflow.features.alltransactions.AllTransactionsViewModel
import com.prof18.moneyflow.features.categories.CategoriesViewModel
import com.prof18.moneyflow.features.home.HomeViewModel
import com.prof18.moneyflow.features.settings.DropboxSyncViewModel
import com.prof18.moneyflow.features.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // View Models
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get())}
    viewModel { AddTransactionViewModel(get(), get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { DropboxSyncViewModel(get(), get()) }
    viewModel { AllTransactionsViewModel(get(), get()) }
}