package com.prof18.moneyflow.presentation.model

// TODO: delete unused fields
data class UIErrorMessage(
    val message: org.jetbrains.compose.resources.StringResource,
    val messageKey: String,
    val messageArgs: List<String> = emptyList(),
    val nerdMessage: org.jetbrains.compose.resources.StringResource,
    val nerdMessageKey: String,
    val nerdMessageArgs: List<String> = emptyList(),
)
