package com.prof18.moneyflow.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.presentation.home.HomeUseCase
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private var useCase: HomeUseCase,
    private val errorMapper: MoneyFlowErrorMapper,
) : ViewModel() {

    var homeModel: HomeModel by mutableStateOf(HomeModel.Loading)
        private set

    val hideSensitiveDataState: StateFlow<Boolean> = useCase.hideSensibleDataState

    init {
        observeHomeModel()
    }

    private fun observeHomeModel() {
        viewModelScope.launch {
            useCase.observeHomeModel()
                .catch { throwable: Throwable ->
                    val error = MoneyFlowError.GetCategories(throwable)
                    throwable.logError(error)
                    val errorMessage = errorMapper.getUIErrorMessage(error)
                    emit(HomeModel.Error(errorMessage))
                }
                .collect {
                    homeModel = it
                }
        }
    }

    fun changeSensitiveDataVisibility(status: Boolean) {
        useCase.toggleHideSensitiveData(status)
    }

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            useCase.deleteTransaction(id)
        }
    }
}
