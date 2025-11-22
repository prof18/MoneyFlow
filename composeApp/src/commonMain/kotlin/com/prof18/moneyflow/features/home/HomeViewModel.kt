package com.prof18.moneyflow.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.data.SettingsRepository
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val moneyRepository: MoneyRepository,
    private val settingsRepository: SettingsRepository,
    private val errorMapper: MoneyFlowErrorMapper,
) : ViewModel() {

    private val _homeModel = MutableStateFlow<HomeModel>(HomeModel.Loading)
    val homeModel: StateFlow<HomeModel> = _homeModel

    val hideSensitiveDataState: StateFlow<Boolean> = settingsRepository.hideSensibleDataState

    init {
        observeHomeModel()
    }

    private fun observeHomeModel() {
        viewModelScope.launch {
            moneyRepository.getMoneySummary()
                .map { summary ->
                    HomeModel.HomeState(
                        balanceRecap = summary.balanceRecap,
                        latestTransactions = summary.latestTransactions,
                    ) as HomeModel
                }
                .catch { throwable: Throwable ->
                    val error = MoneyFlowError.GetCategories(throwable)
                    throwable.logError(error)
                    val errorMessage = errorMapper.getUIErrorMessage(error)
                    emit(HomeModel.Error(errorMessage))
                }
                .collect { model ->
                    _homeModel.value = model
                }
        }
    }

    fun changeSensitiveDataVisibility(status: Boolean) = settingsRepository.setHideSensitiveData(status)

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            val result = runCatching { moneyRepository.deleteTransaction(id) }
                .fold(
                    onSuccess = { MoneyFlowResult.Success(Unit) },
                    onFailure = {
                        val error = MoneyFlowError.DeleteTransaction(it)
                        it.logError(error)
                        MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
                    },
                )

            if (result is MoneyFlowResult.Error) {
                _homeModel.update { HomeModel.Error(result.uiErrorMessage) }
            }
        }
    }
}
