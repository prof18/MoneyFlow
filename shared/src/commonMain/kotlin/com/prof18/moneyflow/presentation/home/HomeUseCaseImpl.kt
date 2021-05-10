package com.prof18.moneyflow.presentation.home

import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.printThrowable
import kotlinx.coroutines.flow.*

class HomeUseCaseImpl(
    private val moneyRepository: MoneyRepository,
) : HomeUseCase {

    override fun observeHomeModel(): Flow<HomeModel> {
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

    override suspend fun deleteTransaction(transactionId: Long) {
        moneyRepository.deleteTransaction(transactionId)
    }
}

