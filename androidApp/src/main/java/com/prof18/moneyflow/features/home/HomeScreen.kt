package com.prof18.moneyflow.features.home

import android.graphics.Color.BLACK
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Position
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.features.home.components.HeaderNavigator
import com.prof18.moneyflow.features.home.components.HomeRecap
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.TransactionCard
import com.prof18.moneyflow.ui.style.AppMargins
import presentation.home.HomeModel
import timber.log.Timber


@Composable
fun HomeScreen(navController: NavController) {

    val homeViewModel = viewModel<HomeViewModel>(
        factory = HomeViewModelFactory()
    )

    val homeModel by homeViewModel.homeLiveData.observeAsState()


    when (homeModel) {
        is HomeModel.Loading -> Loader()
        is HomeModel.HomeState -> {

            val homeState = (homeModel as HomeModel.HomeState)

            Column(modifier = Modifier.padding(AppMargins.small)) {

                TextButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        navController.navigate(Screen.AddTransactionScreen.name)
                    }) {
                    Text("Add transaction")
                }
                HomeRecap(homeState.balanceRecap)
                HeaderNavigator()

                LazyColumnFor(items = homeState.latestTransactions) { transaction ->

                    val (showTransactionMenu, setShowTransactionMenu) = remember {
                        mutableStateOf(
                            false
                        )
                    }

                    DropdownMenu(
                        toggle = {
                            TransactionCard(
                                transaction = transaction,
                                onClick = {
                                    Timber.d("onClick")
                                },
                                onLongPress = {
                                    setShowTransactionMenu(true)
                                })
                            Divider()
                        },
                        expanded = showTransactionMenu,
                        onDismissRequest = { setShowTransactionMenu(false) },
                    ) {
                        DropdownMenuItem(onClick = {
                            homeViewModel.deleteTransaction(transaction.id)
                            setShowTransactionMenu(false)
                        }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
        is HomeModel.Error -> Text("Something wrong here!")
    }
}

