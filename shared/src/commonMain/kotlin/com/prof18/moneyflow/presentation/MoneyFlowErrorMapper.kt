package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.platform.LocalizedStringProvider
import com.prof18.moneyflow.presentation.model.UIErrorMessage

class MoneyFlowErrorMapper(
    private val localizedStringProvider: LocalizedStringProvider
) {

    fun getUIErrorMessage(error: MoneyFlowError): UIErrorMessage {
        return UIErrorMessage(
            message = error.getErrorMessage(),
            nerdMessage = error.getNerdMessage(),
        )
    }

    private fun MoneyFlowError.getErrorMessage(): String {
        return when (this) {
            is MoneyFlowError.AddTransaction -> localizedStringProvider.get("error_add_transaction_message")
            is MoneyFlowError.DeleteTransaction -> localizedStringProvider.get("error_delete_transaction_message")
            is MoneyFlowError.GetAllTransaction -> localizedStringProvider.get("error_get_all_transaction_message")
            is MoneyFlowError.GetCategories -> localizedStringProvider.get("error_get_categories_message")
            is MoneyFlowError.GetMoneySummary -> localizedStringProvider.get("error_get_money_summary_message")
        }
    }

    private fun MoneyFlowError.getNerdMessage(): String {
        return localizedStringProvider.get("error_nerd_message", this.code)
    }
}