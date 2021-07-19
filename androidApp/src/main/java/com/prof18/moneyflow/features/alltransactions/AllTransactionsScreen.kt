package com.prof18.moneyflow.features.alltransactions

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.prof18.moneyflow.ComposeNavigationFactory
import com.prof18.moneyflow.R
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.entities.TransactionTypeUI
import com.prof18.moneyflow.presentation.CategoryIcon
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.components.TransactionCard
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.getViewModel

object AllTransactionsScreenFactory : ComposeNavigationFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.AllTransactionsScreen.route) {
            val viewModel = getViewModel<AllTransactionsViewModel>()

            AllTransactionsScreen(
                navigateUp = { navController.popBackStack() },
                pagingFlow = viewModel.transactionPagingFlow
            )
        }
    }
}

@Composable
fun AllTransactionsScreen(
    navigateUp: () -> Unit = {},
    pagingFlow: Flow<PagingData<MoneyTransaction>>
) {
    Scaffold(
        topBar = {
            MFTopBar(
                topAppBarText = stringResource(id = R.string.all_transactions),
                onBackPressed = { navigateUp() },
            )
        },
        content = {
            val lazyPagingItems = pagingFlow.collectAsLazyPagingItems()

            LazyColumn {

                if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                    item {
                        Loader()
                    }
                }

                // TODO: create some sort of sticky header by grouping by date

                items(lazyPagingItems) { transaction ->

                    if (transaction != null) {
                        TransactionCard(
                            transaction = transaction,
                            onLongPress = { /*TODO*/ },
                            onClick = { /*TODO*/ },
                            hideSensitiveData = false // TODO
                        )
                        Divider()
                    }


                }
            }
        }
    )
}

@Preview
@Composable
private fun AllTransactionsScreenPreviews() {
    MoneyFlowTheme {
        AllTransactionsScreen(
            navigateUp = {},
            pagingFlow = flowOf(
                PagingData.from(
                    listOf(
                        MoneyTransaction(
                            id = 0,
                            title = "Ice Cream",
                            icon = CategoryIcon.IC_ICE_CREAM_SOLID,
                            amount = 10.0,
                            type = TransactionTypeUI.EXPENSE,
                            milliseconds = 0,
                            formattedDate = "12 July 2021"
                        ),
                        MoneyTransaction(
                            id = 1,
                            title = "Tip",
                            icon = CategoryIcon.IC_MONEY_CHECK_ALT_SOLID,
                            amount = 50.0,
                            type = TransactionTypeUI.INCOME,
                            milliseconds = 0,
                            formattedDate = "12 July 2021"
                        )
                    )
                )
            )
        )
    }
}