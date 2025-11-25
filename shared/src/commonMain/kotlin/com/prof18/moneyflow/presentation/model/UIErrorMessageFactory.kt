package com.prof18.moneyflow.presentation.model

import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.amount_not_empty_error
import money_flow.shared.generated.resources.database_file_not_found
import money_flow.shared.generated.resources.error_add_transaction_message
import money_flow.shared.generated.resources.error_database_export
import money_flow.shared.generated.resources.error_database_import
import money_flow.shared.generated.resources.error_delete_transaction_message
import money_flow.shared.generated.resources.error_get_all_transaction_message
import money_flow.shared.generated.resources.error_get_categories_message
import money_flow.shared.generated.resources.error_get_money_summary_message
import money_flow.shared.generated.resources.error_nerd_message
import org.jetbrains.compose.resources.StringResource

fun uiErrorMessageFromKeys(
    messageKey: String,
    nerdMessageKey: String,
    messageArgs: List<String> = emptyList(),
    nerdMessageArgs: List<String> = emptyList(),
): UIErrorMessage = UIErrorMessage(
    message = messageKey.toStringResource(),
    messageKey = messageKey,
    messageArgs = messageArgs,
    nerdMessage = nerdMessageKey.toStringResource(),
    nerdMessageKey = nerdMessageKey,
    nerdMessageArgs = nerdMessageArgs,
)

fun String.toStringResource(): StringResource = when (this) {
    "amount_not_empty_error" -> Res.string.amount_not_empty_error
    "error_add_transaction_message" -> Res.string.error_add_transaction_message
    "error_delete_transaction_message" -> Res.string.error_delete_transaction_message
    "error_get_all_transaction_message" -> Res.string.error_get_all_transaction_message
    "error_get_categories_message" -> Res.string.error_get_categories_message
    "error_get_money_summary_message" -> Res.string.error_get_money_summary_message
    "error_database_export" -> Res.string.error_database_export
    "error_database_import" -> Res.string.error_database_import
    "database_file_not_found" -> Res.string.database_file_not_found
    "error_nerd_message" -> Res.string.error_nerd_message
    else -> Res.string.error_get_money_summary_message
}
