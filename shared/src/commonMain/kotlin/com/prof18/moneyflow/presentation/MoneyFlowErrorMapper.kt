package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowError.AddTransaction
import com.prof18.moneyflow.domain.entities.MoneyFlowError.DatabaseExport
import com.prof18.moneyflow.domain.entities.MoneyFlowError.DatabaseImport
import com.prof18.moneyflow.domain.entities.MoneyFlowError.DeleteTransaction
import com.prof18.moneyflow.domain.entities.MoneyFlowError.DropboxAuth
import com.prof18.moneyflow.domain.entities.MoneyFlowError.GetAllTransaction
import com.prof18.moneyflow.domain.entities.MoneyFlowError.GetCategories
import com.prof18.moneyflow.domain.entities.MoneyFlowError.GetMoneySummary
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
            is AddTransaction -> localizedStringProvider.get("error_add_transaction_message")
            is DeleteTransaction -> localizedStringProvider.get("error_delete_transaction_message")
            is GetAllTransaction -> localizedStringProvider.get("error_get_all_transaction_message")
            is GetCategories -> localizedStringProvider.get("error_get_categories_message")
            is GetMoneySummary -> localizedStringProvider.get("error_get_money_summary_message")
            is DropboxAuth -> localizedStringProvider.get("error_dropbox_auth")
            is DatabaseExport -> TODO()
            is DatabaseImport -> TODO()
        }
    }

    private fun MoneyFlowError.getNerdMessage(): String {
        return localizedStringProvider.get("error_nerd_message", this.code)
    }
}