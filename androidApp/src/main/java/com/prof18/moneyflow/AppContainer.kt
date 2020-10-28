package com.prof18.moneyflow

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import com.prof18.moneyflow.features.addtransaction.AddTransactionScreen
import com.prof18.moneyflow.features.home.HomeScreen

@Composable
fun AppContainer() {

        val navController = rememberNavController()

         MoneyFlowTheme {
            NavHost(navController, startDestination = Screen.HomeScreen.name) {
                composable(Screen.HomeScreen.name) {
                   HomeScreen(navController)
                }

                composable(Screen.AddTransactionScreen.name) {
                    AddTransactionScreen(navController)
                }

                // For sending something
//                composable("profile/{userId}") { backStackEntry ->
//                    Profile(navController, backStackEntry.arguments?.getString("userId"))
//                }
            }
        }
}

