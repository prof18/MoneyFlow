package com.prof18.moneyflow.features.alltransactions

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.prof18.moneyflow.ComposeNavigationFactory
import com.prof18.moneyflow.R
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.entities.TransactionTypeUI
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.ui.components.ErrorView
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.components.TransactionCard
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.getViewModel

internal object AllTransactionsScreenFactory : ComposeNavigationFactory {
    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.AllTransactionsScreen.route) {
            val viewModel = getViewModel<AllTransactionsViewModel>()

            AllTransactionsScreen(
                navigateUp = { navController.popBackStack() },
                getUIErrorMessage = { error -> viewModel.mapErrorToErrorMessage(error) },
                pagingFlow = viewModel.transactionPagingFlow,
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun AllTransactionsScreen(
    navigateUp: () -> Unit = {},
    getUIErrorMessage: (MoneyFlowError) -> UIErrorMessage,
    pagingFlow: Flow<PagingData<MoneyTransaction>>,
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
                if (lazyPagingItems.loadState.refresh is LoadState.Error) {
                    item {
                        val paginationError = (lazyPagingItems.loadState.refresh as LoadState.Error)
                            .error as? PaginationError
                        val uiErrorMessage = if (paginationError != null) {
                            getUIErrorMessage(paginationError.moneyFlowError)
                        } else {
                            UIErrorMessage(
                                message = stringResource(id = R.string.error_generic_message),
                                nerdMessage = "",
                            )
                        }
                        ErrorView(uiErrorMessage = uiErrorMessage)
                    }
                }

                if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                    item {
                        Loader()
                    }
                }

                // TODO: create some sort of sticky header by grouping by date

                items(
                    count = lazyPagingItems.itemCount,
                ) { index ->
                    val transaction = lazyPagingItems[index]
                    if (transaction != null) {
                        TransactionCard(
                            transaction = transaction,
                            onLongPress = { /*TODO: add long press on transaction*/ },
                            onClick = { /*TODO: add click on transaction*/ },
                            hideSensitiveData = false, // TODO: Hide sensitive data on transaction card
                        )
                        Divider()
                    }
                }
            }
        },
    )
}

@Preview(name = "AllTransactionsScreenPreviews Light")
@Preview(name = "AllTransactionsScreenPreviews Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AllTransactionsScreenPreviews() {
    MoneyFlowTheme {
        AllTransactionsScreen(
            navigateUp = {},
            getUIErrorMessage = { UIErrorMessage("", "") },
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
                            formattedDate = "12 July 2021",
                        ),
                        MoneyTransaction(
                            id = 1,
                            title = "Tip",
                            icon = CategoryIcon.IC_MONEY_CHECK_ALT_SOLID,
                            amount = 50.0,
                            type = TransactionTypeUI.INCOME,
                            milliseconds = 0,
                            formattedDate = "12 July 2021",
                        ),
                    ),
                ),
            ),
        )
    }
}
