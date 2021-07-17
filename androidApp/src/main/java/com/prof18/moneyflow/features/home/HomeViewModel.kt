package com.prof18.moneyflow.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.presentation.home.HomeUseCase
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private var useCase: HomeUseCase
) : ViewModel() {

    val homeState: StateFlow<HomeModel> = useCase.observeHomeModel()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = HomeModel.Loading
        )

    var isSensitiveDataVisible by mutableStateOf(true)
        private set

    init {
        // TODO: get data from prefs with a flow
    }

    fun changeSensitiveDataVisibility(visible: Boolean) {
        isSensitiveDataVisible = visible
    }

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            useCase.deleteTransaction(id)
        }
    }
}