package domain.mapper

import data.db.model.InsertTransactionDTO
import data.db.model.TransactionType
import presentation.addtransaction.TransactionToSave
import utils.Utils.generateCurrentMonthId

fun TransactionToSave.mapToInsertTransactionDTO(): InsertTransactionDTO {

    // We save the amount as negative for the outcomes
    var amount = this.amount
    if (this.transactionType == TransactionType.OUTCOME) {
        amount = -amount
    }

    return InsertTransactionDTO(
        dateMillis = this.dateMillis,
        amount = amount,
        description = this.description,
        categoryId = this.categoryId,
        transactionType = this.transactionType,
        currentMonthId = this.dateMillis.generateCurrentMonthId()
    )
}