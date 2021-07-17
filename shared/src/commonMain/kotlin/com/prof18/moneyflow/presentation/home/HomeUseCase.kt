package com.prof18.moneyflow.presentation.home

import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.domain.repository.SettingsRepository
import com.prof18.moneyflow.printThrowable
import kotlinx.coroutines.flow.*

class HomeUseCase(
    private val moneyRepository: MoneyRepository,
    private val settingsRepository: SettingsRepository
) {

    val hideSensibleDataState: StateFlow<Boolean> = settingsRepository.hideSensibleDataState

    fun observeHomeModel(): Flow<HomeModel> {
        return moneyRepository.getMoneySummary().catch { cause: Throwable ->
            printThrowable(cause)
            // TODO: move to error Code
            HomeModel.Error("Something wrong :(")
        }.map {
             HomeModel.HomeState(
                balanceRecap = it.balanceRecap,
                latestTransactions = it.latestTransactions
            )
        }
    }


    fun toggleHideSensitiveData(status: Boolean) {
        settingsRepository.setHideSensitiveData(status)
    }

    suspend fun deleteTransaction(transactionId: Long) {
        moneyRepository.deleteTransaction(transactionId)
    }
}

