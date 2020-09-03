package com.prof18.moneyflow.ui.home

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import com.prof18.moneyflow.style.AppMargins
import com.prof18.moneyflow.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.components.HeaderNavigator
import com.prof18.moneyflow.ui.components.HomeRecap
import com.prof18.moneyflow.ui.components.TransactionCard
import presentation.home.HomeModel
import presentation.home.HomePresenter


@Composable
fun HomeScreen(homePresenter: HomePresenter) {

    val homeState by homePresenter.observeHomeModel().collectAsState(initial = HomeModel.Loading)

    Scaffold(
        bodyContent = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text(text = homeState.toString())
//                HomeRecap()
//                HeaderNavigator()
//
//                ScrollableColumn() {
//                    for (i in 0..5) {
//                        TransactionCard()
//                        Divider()
//                    }
//                }
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
                    // TODO: handle click
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

