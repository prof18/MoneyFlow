package com.prof18.moneyflow.ui.home

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
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
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.prof18.moneyflow.style.AppMargins
import com.prof18.moneyflow.ui.HomeViewModel
import com.prof18.moneyflow.ui.components.HeaderNavigator
import com.prof18.moneyflow.ui.components.HomeRecap
import com.prof18.moneyflow.ui.components.TransactionCard
import data.db.DatabaseSource
import di.recreateDatabaseScope
import org.koin.android.ext.android.getKoin
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.getKoin
import presentation.home.HomeModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val homeModel by viewModel.homeLiveData.observeAsState()



    Scaffold(
        bodyContent = { innerPadding ->

            when (homeModel) {
                is HomeModel.Loading -> CircularProgressIndicator()
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
                    Icon(
                        asset = Icons.Default.Search,
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    // TODO: handle click
                                },
                            )
                            .padding(AppMargins.regular),
                    )

                    Icon(
                        asset = Icons.Default.Settings,
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    // TODO: handle click
                                },
                            )
                            .padding(AppMargins.regular),
                    )
                }
            }
        },

        isFloatingActionButtonDocked = true,

        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    viewModel.refreshData()
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

