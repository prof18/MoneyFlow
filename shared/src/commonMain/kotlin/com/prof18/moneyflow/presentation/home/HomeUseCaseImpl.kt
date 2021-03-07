package com.prof18.moneyflow.presentation.home

import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.repository.MoneyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.prof18.moneyflow.printThrowable

class HomeUseCaseImpl(
    private val moneyRepository: MoneyRepository,
    // That's only for iOs
    private val viewUpdate: ((HomeModel) -> Unit)? = null,
) : HomeUseCase {

    // Used only on iOs
    private val coroutineScope: CoroutineScope = MainScope()

    private val homeModel = MutableStateFlow<HomeModel>(HomeModel.Loading)

    override fun observeHomeModel(): StateFlow<HomeModel> = homeModel

    override fun computeData() {
        coroutineScope.launch {
            computeHomeDataSuspendable()
        }
    }

    override suspend fun computeHomeDataSuspendable() {

        val latestTransactionFlow = moneyRepository.getLatestTransactions()
        val balanceRecapFlow = moneyRepository.getBalanceRecap()

        latestTransactionFlow.combine(balanceRecapFlow) { transactions: List<MoneyTransaction>, balanceRecap: BalanceRecap ->
            HomeModel.HomeState(
                balanceRecap = balanceRecap,
                latestTransactions = transactions
            )
        }.catch { cause: Throwable ->
            printThrowable(cause)
            // TODO: move to error Code
            val error = HomeModel.Error("Something wrong")
            homeModel.value = error
            viewUpdate?.invoke(error)
        }.collect {
            homeModel.value = it
            viewUpdate?.invoke(it)
        }
    }

    override fun deleteTransaction(transactionId: Long) {
        coroutineScope.launch {
            moneyRepository.deleteTransaction(transactionId)
        }
    }

    override suspend fun deleteTransactionSuspendable(transactionId: Long) {
        moneyRepository.deleteTransaction(transactionId)
    }

    // iOs only
    fun onDestroy() {
        coroutineScope.cancel()
    }
}

