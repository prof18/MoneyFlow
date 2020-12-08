package data.mapper

import data.db.model.TransactionType
import presentation.addtransaction.TransactionToSave
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionMapperTest {

    @Test
    fun mapToInsertTransactionDTO_MapsCorrectlyWithIncome() {

        val transactionToSave = TransactionToSave(
            dateMillis = 123456,
            amount = 10.0,
            description = "asd",
            categoryId = 5,
            transactionType = TransactionType.INCOME
        )

        val transactionDTO = transactionToSave.mapToInsertTransactionDTO()

        assertEquals(123456, transactionDTO.dateMillis)
        assertEquals(10.0, transactionDTO.amount)
        assertEquals("asd", transactionDTO.description)
        assertEquals(5, transactionDTO.categoryId)
        assertEquals(TransactionType.INCOME, transactionDTO.transactionType)
    }

    @Test
    fun mapToInsertTransactionDTO_MapsCorrectlyWithOutcome() {

        val transactionToSave = TransactionToSave(
            dateMillis = 123456,
            amount = 10.0,
            description = "asd",
            categoryId = 5,
            transactionType = TransactionType.OUTCOME
        )

        val transactionDTO = transactionToSave.mapToInsertTransactionDTO()

        assertEquals(123456, transactionDTO.dateMillis)
        assertEquals(-10.0, transactionDTO.amount)
        assertEquals("asd", transactionDTO.description)
        assertEquals(5, transactionDTO.categoryId)
        assertEquals(TransactionType.OUTCOME, transactionDTO.transactionType)

    }

}