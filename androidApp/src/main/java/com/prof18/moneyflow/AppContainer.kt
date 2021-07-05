package com.prof18.moneyflow

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.prof18.moneyflow.features.addtransaction.AddTransactionScreen
import com.prof18.moneyflow.features.budget.BudgetScreen
import com.prof18.moneyflow.features.categories.CategoriesScreen
import com.prof18.moneyflow.features.categories.data.CategoryUIData
import com.prof18.moneyflow.features.home.HomeScreen
import com.prof18.moneyflow.features.recap.RecapScreen
import com.prof18.moneyflow.features.settings.SettingsScreen
import com.prof18.moneyflow.ui.style.LightAppColors
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
fun AppContainer() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    MoneyFlowTheme {
        Scaffold(
            bottomBar = {
                val currentDestination = navBackStackEntry?.destination

                if (canShowBottomBar(navBackStackEntry)) {

                    BottomNavigation {
                        bottomNavigationItems.forEach { tabBarItem ->
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = tabBarItem.drawableResId),
                                        contentDescription = null,
                                        modifier = Modifier.size(22.dp)
                                    )
                                },
                                label = { Text(stringResource(tabBarItem.titleResId)) },
                                selected = currentDestination?.hierarchy?.any { it.route == tabBarItem.screen.name } == true,
                                unselectedContentColor = LightAppColors.lightGrey.copy(alpha = 0.3f),
                                onClick = {
                                    navController.navigate(tabBarItem.screen.name) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }

                                }
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->

            NavHost(navController, startDestination = Screen.HomeScreen.name) {
                composable(Screen.HomeScreen.name) {
                    HomeScreen(navController, paddingValues)
                }

                composable(Screen.AddTransactionScreen.name) {

                    // Get back the category
                    val category = it.savedStateHandle
                        .getLiveData<CategoryUIData>(NavigationArguments.CATEGORY)
                        .observeAsState()

                    AddTransactionScreen(
                        navController = navController,
                        categoryName = category.value?.name,
                        categoryId = category.value?.id,
                        categoryIcon = category.value?.icon
                    )
                }

                composable(
                    route = Screen.CategoriesScreen.name + "/{${NavigationArguments.FROM_ADD_TRANSACTION}}",
                    arguments = listOf(navArgument(NavigationArguments.FROM_ADD_TRANSACTION) {
                        type = NavType.BoolType
                    })
                ) { backStackEntry ->
                    CategoriesScreen(
                        navController,
                        backStackEntry.arguments?.getBoolean(NavigationArguments.FROM_ADD_TRANSACTION)
                            ?: false
                    )
                }

                composable(Screen.RecapScreen.name) {
                    RecapScreen()
                }

                composable(Screen.BudgetScreen.name) {
                    BudgetScreen()
                }

                composable(Screen.SettingsScreen.name) {
                    SettingsScreen()
                }

            }

        }


    }
}

// TODO: improve or delete
private fun canShowBottomBar(navBackStackEntry: NavBackStackEntry?): Boolean {
    val currentDestination = navBackStackEntry?.destination?.route
    if (currentDestination != null) {
        return currentDestination.contains(Screen.HomeScreen.name) ||
                currentDestination.contains(Screen.RecapScreen.name) ||
                currentDestination.contains(Screen.BudgetScreen.name) ||
                currentDestination.contains(Screen.SettingsScreen.name)
    }
    return false

}


