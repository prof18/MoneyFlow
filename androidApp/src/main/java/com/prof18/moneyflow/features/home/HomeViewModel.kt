package com.prof18.moneyflow.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.presentation.home.HomeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private var useCase: HomeUseCase
) : ViewModel() {

    val homeState = useCase.observeHomeModel()
        .stateIn(viewModelScope, SharingStarted.Eagerly, HomeModel.Loading)

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            useCase.deleteTransaction(id)
        }
    }
}