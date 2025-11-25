package com.prof18.moneyflow.di

import com.prof18.moneyflow.MainViewModel
import com.prof18.moneyflow.features.addtransaction.AddTransactionViewModel
import com.prof18.moneyflow.features.alltransactions.AllTransactionsViewModel
import com.prof18.moneyflow.features.categories.CategoriesViewModel
import com.prof18.moneyflow.features.home.HomeViewModel
import com.prof18.moneyflow.features.settings.SettingsViewModel
import com.prof18.moneyflow.features.settings.BackupManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { BackupManager(get()) }
    // View Models
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { AddTransactionViewModel(get(), get()) }
    viewModel { CategoriesViewModel(get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { AllTransactionsViewModel(get(), get()) }
}
