package com.prof18.moneyflow.presentation.addtransaction

import com.prof18.moneyflow.domain.repository.MoneyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AddTransactionUseCaseImpl(
    private val moneyRepository: MoneyRepository,
    private val onInsertDone: (() -> Unit)? = null, // Called on iOs only
) : AddTransactionUseCase {

    // Used only on iOs
    private val coroutineScope: CoroutineScope = MainScope()

    override fun insertTransaction(transactionToSave: TransactionToSave) {
        coroutineScope.launch {
            insertTransactionSuspendable(transactionToSave)
            onInsertDone?.invoke()
        }
    }

    override suspend fun insertTransactionSuspendable(transactionToSave: TransactionToSave) {
        moneyRepository.insertTransaction(transactionToSave)
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }
}