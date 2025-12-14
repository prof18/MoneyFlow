package com.prof18.moneyflow.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface AppRoute

@Serializable
@SerialName("home")
internal data object HomeRoute : AppRoute

@Serializable
@SerialName("settings")
internal data object SettingsRoute : AppRoute

@Serializable
@SerialName("add_transaction")
internal data object AddTransactionRoute : AppRoute

@Serializable
@SerialName("categories")
internal data class CategoriesRoute(val fromAddTransaction: Boolean) : AppRoute

@Serializable
@SerialName("all_transactions")
internal data object AllTransactionsRoute : AppRoute
