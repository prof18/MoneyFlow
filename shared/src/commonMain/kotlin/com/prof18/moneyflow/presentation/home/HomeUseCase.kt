package com.prof18.moneyflow.presentation.home

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.domain.repository.SettingsRepository
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class HomeUseCase(
    private val moneyRepository: MoneyRepository,
    private val settingsRepository: SettingsRepository
) {

    val hideSensibleDataState: StateFlow<Boolean> = settingsRepository.hideSensibleDataState

    fun observeHomeModel(): Flow<HomeModel> =
        moneyRepository.getMoneySummary()
            .catch { throwable: Throwable ->
                val error = MoneyFlowError.GetMoneySummary(throwable)
                throwable.logError(error)
                HomeModel.Error(error)
            }.map {
                HomeModel.HomeState(
                    balanceRecap = it.balanceRecap,
                    latestTransactions = it.latestTransactions
                )
            }

    fun toggleHideSensitiveData(status: Boolean) {
        settingsRepository.setHideSensitiveData(status)
    }

    suspend fun deleteTransaction(transactionId: Long): MoneyFlowResult<Unit> {
        return try {
            moneyRepository.deleteTransaction(transactionId)
            MoneyFlowResult.Success(Unit)
        } catch (throwable: Throwable) {
            val error = MoneyFlowError.DeleteTransaction(throwable)
            throwable.logError(error)
            MoneyFlowResult.Error(error)
        }
    }
}

