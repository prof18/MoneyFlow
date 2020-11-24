package com.prof18.moneyflow

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class Screen(val name: String) {
    object HomeScreenContainer : Screen("HomeScreenContainer")
    object AddTransactionScreen : Screen("AddTransactionScreen")
    object CategoriesScreen : Screen("CategoriesScreen")
    object HomeScreen: Screen("HomeScreen")
    object RecapScreen: Screen("RecapScreen")
    object BudgetScreen: Screen("BudgetScreen")
    object SettingsScreen: Screen("SettingsScreen")
}

object NavigationArguments {
    const val FROM_ADD_TRANSACTION = "fromAddTransaction"
    const val CATEGORY = "category"
}

data class BottomNavigationItem(
    val screen: Screen,
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
)

val bottomNavigationItems = listOf(

    BottomNavigationItem(
        screen = Screen.HomeScreen,
        titleResId = R.string.home_screen,
        drawableResId = R.drawable.ic_home_solid
    ),

    BottomNavigationItem(
        screen = Screen.RecapScreen,
        titleResId = R.string.recap_screen,
        drawableResId = R.drawable.ic_chart_pie_solid
    ),

    BottomNavigationItem(
        screen = Screen.BudgetScreen,
        titleResId = R.string.budget_screen,
        drawableResId = R.drawable.ic_balance_scale_left_solid
    ),

    BottomNavigationItem(
        screen = Screen.SettingsScreen,
        titleResId = R.string.settings_screen,
        drawableResId = R.drawable.ic_cog_solid
    ),

)

