package com.prof18.moneyflow.di

import com.prof18.moneyflow.MainViewModel
import com.prof18.moneyflow.features.addtransaction.AddTransactionViewModel
import com.prof18.moneyflow.features.alltransactions.AllTransactionsViewModel
import com.prof18.moneyflow.features.categories.CategoriesViewModel
import com.prof18.moneyflow.features.home.HomeViewModel
import com.prof18.moneyflow.features.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // View Models
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AddTransactionViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::AllTransactionsViewModel)
}
