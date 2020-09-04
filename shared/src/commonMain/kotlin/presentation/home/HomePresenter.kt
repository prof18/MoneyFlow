package presentation.home

import BasePresenter
import domain.model.BalanceRecap
import domain.model.Transaction
import domain.repository.MoneyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomePresenter(
    private val moneyRepository: MoneyRepository,
    private val coroutineScope: CoroutineScope = MainScope() // we provide a default value for iOs.
) : BasePresenter<HomeView>() {

    private val homeModel = MutableStateFlow<HomeModel>(HomeModel.Loading)

    fun observeHomeModel(): Flow<HomeModel> = homeModel

    override fun onViewAttached(view: HomeView) {
        super.onViewAttached(view)
        // TODO: start some action?
    }

    fun computeHomeData() {
        val latestTransactionFlow = moneyRepository.getLatestTransactions()
        val balanceRecapFlow = moneyRepository.getBalanceRecap()
        coroutineScope.launch {
            latestTransactionFlow.combine(balanceRecapFlow) { transactions: List<Transaction>, balanceRecap: BalanceRecap ->
                HomeModel.HomeState(
                    balanceRecap = balanceRecap,
                    latestTransactions = transactions
                )
            }.collect {
                homeModel.value = it
            }
        }
    }

    override fun onViewDetached() {
        super.onViewDetached()
        coroutineScope.cancel()
    }
}

