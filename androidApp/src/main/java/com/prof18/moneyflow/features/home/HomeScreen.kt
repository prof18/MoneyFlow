package com.prof18.moneyflow.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
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

                Button(onClick = {
                    navController.navigate(Screen.AddTransactionScreen.name)
                } ) {
                    Text("Click")
                }
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
}

