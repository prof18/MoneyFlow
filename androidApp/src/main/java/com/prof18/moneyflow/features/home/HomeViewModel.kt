package com.prof18.moneyflow.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.presentation.home.HomeUseCase
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private var useCase: HomeUseCase
) : ViewModel() {

    val homeState: StateFlow<HomeModel> = useCase.observeHomeModel()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = HomeModel.Loading
        )


    val hideSensitiveDataState: StateFlow<Boolean> = useCase.hideSensibleDataState


    fun changeSensitiveDataVisibility(status: Boolean) {
        useCase.toggleHideSensitiveData(status)
    }

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            useCase.deleteTransaction(id)
        }
    }
}
