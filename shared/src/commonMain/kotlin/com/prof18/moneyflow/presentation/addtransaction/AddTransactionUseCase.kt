package com.prof18.moneyflow.presentation.addtransaction


interface AddTransactionUseCase {

    @Throws(Exception::class)
    fun insertTransaction(transactionToSave: TransactionToSave)

    @Throws(Exception::class)
    suspend fun insertTransactionSuspendable(transactionToSave: TransactionToSave)

}