package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowError.AddTransaction
import com.prof18.moneyflow.domain.entities.MoneyFlowError.DatabaseExport
import com.prof18.moneyflow.domain.entities.MoneyFlowError.DatabaseImport
import com.prof18.moneyflow.domain.entities.MoneyFlowError.DatabaseNotFound
import com.prof18.moneyflow.domain.entities.MoneyFlowError.DeleteTransaction
import com.prof18.moneyflow.domain.entities.MoneyFlowError.GetAllTransaction
import com.prof18.moneyflow.domain.entities.MoneyFlowError.GetCategories
import com.prof18.moneyflow.domain.entities.MoneyFlowError.GetMoneySummary
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.database_file_not_found
import money_flow.shared.generated.resources.error_add_transaction_message
import money_flow.shared.generated.resources.error_database_export
import money_flow.shared.generated.resources.error_database_import
import money_flow.shared.generated.resources.error_delete_transaction_message
import money_flow.shared.generated.resources.error_get_all_transaction_message
import money_flow.shared.generated.resources.error_get_categories_message
import money_flow.shared.generated.resources.error_get_money_summary_message

internal class MoneyFlowErrorMapper {

    fun getUIErrorMessage(error: MoneyFlowError): UIErrorMessage {
        return UIErrorMessage(
            message = error.getErrorMessageRes(),
        )
    }

    private fun MoneyFlowError.getErrorMessageRes() = when (this) {
        is AddTransaction -> Res.string.error_add_transaction_message
        is DeleteTransaction -> Res.string.error_delete_transaction_message
        is GetAllTransaction -> Res.string.error_get_all_transaction_message
        is GetCategories -> Res.string.error_get_categories_message
        is GetMoneySummary -> Res.string.error_get_money_summary_message
        is DatabaseExport -> Res.string.error_database_export
        is DatabaseImport -> Res.string.error_database_import
        is DatabaseNotFound -> Res.string.database_file_not_found
    }
}
