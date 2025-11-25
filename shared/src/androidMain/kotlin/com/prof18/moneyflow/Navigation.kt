package com.prof18.moneyflow

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.DrawableResource

internal typealias ComposeNavigationFactory = (NavGraphBuilder, NavController) -> Unit

internal sealed class Screen(val route: String) {
    object AddTransactionScreen : Screen("add_transaction_screen")
    object CategoriesScreen : Screen("categories_screen")
    object HomeScreen : Screen("home_screen")
    object RecapScreen : Screen("recap_screen")
    object BudgetScreen : Screen("budget_screen")
    object SettingsScreen : Screen("settings_screen")
    object AllTransactionsScreen : Screen("all_transactions_screen")
}

internal sealed class NavigationArguments(val key: String) {
    object FromAddTransaction : NavigationArguments("from_add_transaction")
    object Category : NavigationArguments("category")
}

internal data class BottomNavigationItem(
    val screen: Screen,
    val titleRes: StringResource,
    val drawableRes: DrawableResource,
)

internal val bottomNavigationItems = listOf(

    BottomNavigationItem(
        screen = Screen.HomeScreen,
        titleRes = Res.string.home_screen,
        drawableRes = Res.drawable.ic_home_solid,
    ),

//    // Coming Soon
//    BottomNavigationItem(
//        screen = Screen.RecapScreen,
//        titleRes = Res.string.recap_screen,
//        drawableRes = Res.drawable.ic_chart_pie_solid
//    ),
//
//    // Coming Soon
//    BottomNavigationItem(
//        screen = Screen.BudgetScreen,
//        titleRes = Res.string.budget_screen,
//        drawableRes = Res.drawable.ic_balance_scale_left_solid
//    ),

    BottomNavigationItem(
        screen = Screen.SettingsScreen,
        titleRes = Res.string.settings_screen,
        drawableRes = Res.drawable.ic_cog_solid,
    ),

)
