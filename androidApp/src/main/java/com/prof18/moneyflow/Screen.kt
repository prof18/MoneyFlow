package com.prof18.moneyflow

sealed class Screen(val name: String) {
    object HomeScreen : Screen("HomeScreen")
    object AddTransactionScreen : Screen("AddTransactionScreen")
}