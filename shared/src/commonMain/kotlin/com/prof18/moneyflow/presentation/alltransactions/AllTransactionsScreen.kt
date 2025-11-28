package com.prof18.moneyflow.presentation.alltransactions

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.entities.TransactionTypeUI
import com.prof18.moneyflow.features.alltransactions.AllTransactionsUiState
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.ui.components.ErrorView
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.components.TransactionCard
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.all_transactions
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AllTransactionsScreen(
    stateFlow: StateFlow<AllTransactionsUiState>,
    loadNextPage: () -> Unit,
    navigateUp: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            MFTopBar(
                topAppBarText = stringResource(Res.string.all_transactions),
                onBackPressed = { navigateUp() },
            )
        },
        content = {
            val uiState = stateFlow.collectAsState().value

            LazyColumn {
                when {
                    uiState.error != null -> {
                        item { ErrorView(uiErrorMessage = uiState.error) }
                    }
                    uiState.isLoading -> item { Loader() }
                    else -> {
                        // TODO: create some sort of sticky header by grouping by date
                        items(
                            count = uiState.transactions.size,
                        ) { index ->
                            val transaction = uiState.transactions[index]
                            TransactionCard(
                                transaction = transaction,
                                onLongPress = { /*TODO: add long press on transaction*/ },
                                onClick = { /*TODO: add click on transaction*/ },
                                hideSensitiveData = false, // TODO: Hide sensitive data on transaction card
                            )
                            Divider()

                            if (
                                index == uiState.transactions.lastIndex &&
                                !uiState.endReached &&
                                !uiState.isLoadingMore
                            ) {
                                loadNextPage()
                            }
                        }

                        if (uiState.isLoadingMore) {
                            item { Loader() }
                        }
                    }
                }
            }
        },
    )
}

@Preview(name = "AllTransactionsScreenPreviews Light")
@Composable
private fun AllTransactionsScreenPreviews() {
    MoneyFlowTheme {
        AllTransactionsScreen(
            navigateUp = {},
            stateFlow = MutableStateFlow(
                AllTransactionsUiState(
                    transactions = listOf(
                        SampleTransactions.iceCream,
                        SampleTransactions.tip,
                    ),
                ),
            ),
            loadNextPage = {},
        )
    }
}

private object SampleTransactions {
    val iceCream = MoneyTransaction(
        id = 0,
        title = "Ice Cream",
        icon = CategoryIcon.IC_ICE_CREAM_SOLID,
        amount = 10.0,
        type = TransactionTypeUI.EXPENSE,
        milliseconds = 0,
        formattedDate = "12 July 2021",
    )

    val tip = MoneyTransaction(
        id = 1,
        title = "Tip",
        icon = CategoryIcon.IC_MONEY_CHECK_ALT_SOLID,
        amount = 50.0,
        type = TransactionTypeUI.INCOME,
        milliseconds = 0,
        formattedDate = "12 July 2021",
    )
}
