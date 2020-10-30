package com.prof18.moneyflow

sealed class Screen(val name: String) {
    object HomeScreen : Screen("HomeScreen")
    object AddTransactionScreen : Screen("AddTransactionScreen")
    object CategoriesScreen : Screen("CategoriesScreen")
}

object NavigationArguments {
    const val FROM_ADD_TRANSACTION = "fromAddTransaction"
    const val CATEGORY = "category"
}