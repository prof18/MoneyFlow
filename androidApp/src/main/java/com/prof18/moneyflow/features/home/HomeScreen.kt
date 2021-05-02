package com.prof18.moneyflow.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.features.home.components.HeaderNavigator
import com.prof18.moneyflow.features.home.components.HomeRecap
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.TransactionCard
import com.prof18.moneyflow.ui.style.AppMargins
import org.koin.androidx.compose.getViewModel
import timber.log.Timber


@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues) {

    val homeViewModel = getViewModel<HomeViewModel>()

    val homeModel by homeViewModel.homeState.collectAsState()

    when (homeModel) {
        is HomeModel.Loading -> Loader()
        is HomeModel.HomeState -> {

            val homeState = (homeModel as HomeModel.HomeState)

            Column(modifier = Modifier.padding(AppMargins.small)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "My Wallet",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .padding(horizontal = AppMargins.regular)
                            .padding(top = AppMargins.regular)
                    )

                    IconButton(
                        onClick = {
                            navController.navigate(Screen.AddTransactionScreen.name)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = AppMargins.small)
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = null,
                        )
                    }
                }

                HomeRecap(homeState.balanceRecap)
                HeaderNavigator()

                if (homeState.latestTransactions.isEmpty()) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "¯\\_(ツ)_/¯",
                                modifier = Modifier
                                    .padding(bottom = AppMargins.small),
                                style = MaterialTheme.typography.h6
                            )

                            Text(
                                "Your wallet is emptyyy",
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                } else {


                    LazyColumn(
                        modifier = Modifier
                            .padding(bottom = paddingValues.calculateBottomPadding())
                    ) {
                        items(homeState.latestTransactions) { transaction ->
                            val (showTransactionMenu, setShowTransactionMenu) = remember {
                                mutableStateOf(
                                    false
                                )
                            }

                            Box(modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.TopStart)) {
                                TransactionCard(
                                    transaction = transaction,
                                    onClick = {
                                        Timber.d("onClick")
                                    },
                                    onLongPress = {
                                        setShowTransactionMenu(true)
                                    })
                                DropdownMenu(
                                    expanded = showTransactionMenu,
                                    onDismissRequest = { setShowTransactionMenu(false) }
                                ) {
                                    DropdownMenuItem(onClick = {
                                        homeViewModel.deleteTransaction(transaction.id)
                                        setShowTransactionMenu(false)
                                    }) {
                                        Text("Delete")
                                    }
                                }
                            }
                            Divider()
                        }
                    }
                }
            }
        }
        is HomeModel.Error -> Text("Something wrong here!")
    }
}

