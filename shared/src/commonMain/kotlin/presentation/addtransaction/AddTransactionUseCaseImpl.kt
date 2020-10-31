package presentation.addtransaction

import InsertTransactionDTO
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

    override fun insertTransaction(insertTransactionDTO: InsertTransactionDTO) {
        coroutineScope.launch {
            insertTransactionSuspendable(insertTransactionDTO)
        }
    }

    override suspend fun insertTransactionSuspendable(insertTransactionDTO: InsertTransactionDTO) {
        moneyRepository.insertTransaction(insertTransactionDTO)
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }
}