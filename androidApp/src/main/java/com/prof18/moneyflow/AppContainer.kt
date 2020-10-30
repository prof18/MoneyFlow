package com.prof18.moneyflow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.prof18.moneyflow.features.addtransaction.AddTransactionScreen
import com.prof18.moneyflow.features.categories.CategoriesScreen
import com.prof18.moneyflow.features.categories.data.CategoryUIData
import com.prof18.moneyflow.features.home.HomeScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
fun AppContainer() {

    val navController = rememberNavController()

    MoneyFlowTheme {
        NavHost(navController, startDestination = Screen.HomeScreen.name) {
            composable(Screen.HomeScreen.name) {
                HomeScreen(navController)
            }

            composable(Screen.AddTransactionScreen.name) {

                // Get back the category
                val category = it.savedStateHandle.getLiveData<CategoryUIData>(NavigationArguments.CATEGORY).observeAsState()

                AddTransactionScreen(
                    navController = navController,
                    categoryName = category.value?.name,
                    categoryId = category.value?.id
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

        }
    }
}

