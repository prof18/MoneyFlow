package com.prof18.moneyflow.presentation.home

import co.touchlab.kermit.Logger
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.domain.repository.SettingsRepository
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
            .catch { cause: Throwable ->
                Logger.w(cause) { "Error while getting Categories" }
                // TODO: move to error Code
                HomeModel.Error("Something wrong :(")
            }.map {
                HomeModel.HomeState(
                    balanceRecap = it.balanceRecap,
                    latestTransactions = it.latestTransactions
                )
            }


    fun toggleHideSensitiveData(status: Boolean) {
        settingsRepository.setHideSensitiveData(status)
    }

    suspend fun deleteTransaction(transactionId: Long) {
        moneyRepository.deleteTransaction(transactionId)
    }
}

