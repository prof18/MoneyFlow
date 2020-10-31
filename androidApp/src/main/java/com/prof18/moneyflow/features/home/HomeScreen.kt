package com.prof18.moneyflow.features.home

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.features.home.components.HeaderNavigator
import com.prof18.moneyflow.features.home.components.HomeRecap
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.TransactionCard
import presentation.home.HomeModel


@Composable
fun HomeScreen(navController: NavController) {

    val homeViewModel = viewModel<HomeViewModel>(
        factory = HomeViewModelFactory()
    )

    val homeModel by homeViewModel.homeLiveData.observeAsState()


    Scaffold(
        bodyContent = { innerPadding ->

            when (homeModel) {
                is HomeModel.Loading -> Loader()
                is HomeModel.HomeState -> {

                    val homeState = (homeModel as HomeModel.HomeState)

                    Column(modifier = Modifier.padding(innerPadding)) {

                        HomeRecap(homeState.balanceRecap)
                        HeaderNavigator()

                        LazyColumnFor(items = homeState.latestTransactions) {
                            TransactionCard(it)
                            Divider()
                        }
                    }
                }
                is HomeModel.Error -> Text("Something wrong here!")
            }
        },

        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Modifier
                        .clickable(
                            onClick = {
                                // TODO: handle click
                            },
                        )
                        .padding(AppMargins.regular)
                    Icon(asset = Icons.Default.Search)

                    Modifier
                        .clickable(
                            onClick = {
                                // TODO: handle click
                            },
                        )
                        .padding(AppMargins.regular)
                    Icon(asset = Icons.Default.Settings)
                }
            }
        },

        isFloatingActionButtonDocked = true,

        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    navController.navigate(Screen.AddTransactionScreen.name)
                },
            ) {
                Icon(asset = Icons.Default.Add)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    )
}


/*

    Bottom navigation to use later on

    val selectedItem = state { 0 }
    val items = listOf(
        NavigationItem("Call", Icons.Filled.Call),
        NavigationItem("People", Icons.Filled.Face),
        NavigationItem("Email", Icons.Filled.Email)
    )

    BottomNavigation {
                for (i in 0..3) {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Email) },
                        label = { Text(text = "label") },
                        selected = i == 0,
                        onSelect = {  }
                    )
                }
            }


 */

//
//@Preview
//@Composable
//fun HomeScreenPreview() {
//    MoneyFlowTheme {
//        HomeScreen()
//    }
//}
//
//@Preview
//@Composable
//fun HomeScreenDarkPreview() {
//    MoneyFlowTheme(darkTheme = true) {
//        HomeScreen()
//    }
//}

