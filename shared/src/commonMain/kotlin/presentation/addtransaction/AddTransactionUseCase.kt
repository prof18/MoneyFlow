package presentation.addtransaction

import InsertTransactionDTO


interface AddTransactionUseCase {

    @Throws(Exception::class)
    fun insertTransaction(insertTransactionDTO: InsertTransactionDTO)

    @Throws(Exception::class)
    suspend fun insertTransactionSuspendable(insertTransactionDTO: InsertTransactionDTO)

}