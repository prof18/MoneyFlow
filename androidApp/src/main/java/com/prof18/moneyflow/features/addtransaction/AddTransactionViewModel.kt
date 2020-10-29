package com.prof18.moneyflow.features.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prof18.moneyflow.features.home.HomeViewModel
import org.koin.java.KoinJavaComponent

class AddTransactionViewModel: ViewModel() {




}

class AddTransactionViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
            return AddTransactionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}