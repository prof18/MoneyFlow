package com.prof18.moneyflow.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute

@Serializable
@SerialName("home")
data object HomeRoute : AppRoute

@Serializable
@SerialName("settings")
data object SettingsRoute : AppRoute

@Serializable
@SerialName("add_transaction")
data object AddTransactionRoute : AppRoute

@Serializable
@SerialName("categories")
data class CategoriesRoute(val fromAddTransaction: Boolean) : AppRoute

@Serializable
@SerialName("all_transactions")
data object AllTransactionsRoute : AppRoute
