package presentation.addtransaction

import domain.repository.MoneyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AddTransactionUseCaseImpl(
    private val moneyRepository: MoneyRepository,
): AddTransactionUseCase {

    // Used only on iOs
    private val coroutineScope: CoroutineScope = MainScope()

    override fun insertTransaction(transactionToSave: TransactionToSave) {
        coroutineScope.launch {
            insertTransactionSuspendable(transactionToSave)
        }
    }

    override suspend fun insertTransactionSuspendable(transactionToSave: TransactionToSave) {
        moneyRepository.insertTransaction(transactionToSave)
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }
}