package com.prof18.moneyflow.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.touchlab.kermit.Logger
import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.entities.TransactionTypeUI
import com.prof18.moneyflow.presentation.home.components.HeaderNavigator
import com.prof18.moneyflow.presentation.home.components.HomeRecap
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.ui.components.ErrorView
import com.prof18.moneyflow.ui.components.Loader
import com.prof18.moneyflow.ui.components.TransactionCard
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.delete
import money_flow.shared.generated.resources.empty_wallet
import money_flow.shared.generated.resources.error_get_money_summary_message
import money_flow.shared.generated.resources.error_nerd_message
import money_flow.shared.generated.resources.hide_sensitive_data
import money_flow.shared.generated.resources.latest_transactions
import money_flow.shared.generated.resources.my_wallet
import money_flow.shared.generated.resources.show_sensitive_data
import money_flow.shared.generated.resources.shrug
import org.jetbrains.compose.resources.stringResource

@Composable
@Suppress("LongMethod") // TODO: reduce method length
internal fun HomeScreen(
    homeModel: HomeModel,
    hideSensitiveDataState: Boolean,
    navigateToAllTransactions: () -> Unit,
    navigateToAddTransaction: () -> Unit = {},
    paddingValues: PaddingValues,
    deleteTransaction: (Long) -> Unit = {},
    changeSensitiveDataVisibility: (Boolean) -> Unit = {},
) {
    when (homeModel) {
        is HomeModel.Loading -> Loader()
        is HomeModel.HomeState -> {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(Margins.small),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(Res.string.my_wallet),
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .padding(horizontal = Margins.regular)
                            .padding(top = Margins.regular),
                    )

                    Row {
                        IconButton(
                            onClick = { changeSensitiveDataVisibility(hideSensitiveDataState.not()) },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(top = Margins.small),
                        ) {
                            if (hideSensitiveDataState) {
                                Icon(
                                    Icons.Rounded.Visibility,
                                    contentDescription = stringResource(Res.string.show_sensitive_data),
                                )
                            } else {
                                Icon(
                                    Icons.Rounded.VisibilityOff,
                                    contentDescription = stringResource(Res.string.hide_sensitive_data),
                                )
                            }
                        }

                        IconButton(
                            onClick = { navigateToAddTransaction() },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(top = Margins.small),
                        ) {
                            Icon(
                                Icons.Rounded.Add,
                                contentDescription = null,
                            )
                        }
                    }
                }

                HomeRecap(
                    balanceRecap = homeModel.balanceRecap,
                    hideSensitiveData = hideSensitiveDataState,
                )
                HeaderNavigator(
                    title = stringResource(Res.string.latest_transactions),
                    onClick = navigateToAllTransactions,
                )

                if (homeModel.latestTransactions.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                stringResource(Res.string.shrug),
                                modifier = Modifier
                                    .padding(bottom = Margins.small),
                                style = MaterialTheme.typography.h6,
                            )

                            Text(
                                stringResource(Res.string.empty_wallet),
                                style = MaterialTheme.typography.h6,
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(bottom = paddingValues.calculateBottomPadding()),
                    ) {
                        items(homeModel.latestTransactions) { transaction ->
                            val (showTransactionMenu, setShowTransactionMenu) = remember {
                                mutableStateOf(
                                    false,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.TopStart),
                            ) {
                                TransactionCard(
                                    transaction = transaction,
                                    onClick = {
                                        Logger.d { "onClick" }
                                    },
                                    onLongPress = {
                                        setShowTransactionMenu(true)
                                    },
                                    hideSensitiveData = hideSensitiveDataState,
                                )
                                DropdownMenu(
                                    expanded = showTransactionMenu,
                                    onDismissRequest = { setShowTransactionMenu(false) },
                                ) {
                                    DropdownMenuItem(onClick = {
                                        deleteTransaction(transaction.id)
                                        setShowTransactionMenu(false)
                                    }) {
                                        Text(stringResource(Res.string.delete))
                                    }
                                }
                            }
                            Divider()
                        }
                    }
                }
            }
        }
        is HomeModel.Error -> ErrorView(uiErrorMessage = homeModel.uiErrorMessage)
    }
}

@Preview(name = "HomeScreen Light")
@Composable
private fun HomeScreenPreview() {
    MoneyFlowTheme {
        Surface {
            HomeScreen(
                homeModel = HomeModel.HomeState(
                    balanceRecap = BalanceRecap(
                        totalBalance = 5000.0,
                        monthlyIncome = 1000.0,
                        monthlyExpenses = 50.0,
                    ),
                    latestTransactions = listOf(
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
                hideSensitiveDataState = true,
                navigateToAllTransactions = {},
                paddingValues = PaddingValues(),
            )
        }
    }
}

@Preview(name = "HomeScreenError Light")
@Composable
private fun HomeScreenErrorPreview() {
    MoneyFlowTheme {
        Surface {
            HomeScreen(
                homeModel = HomeModel.Error(
                    UIErrorMessage(
                        message = Res.string.error_get_money_summary_message,
                        messageKey = "error_get_money_summary_message",
                        nerdMessage = Res.string.error_nerd_message,
                        nerdMessageKey = "error_nerd_message",
                        nerdMessageArgs = listOf("101"),
                    ),
                ),
                hideSensitiveDataState = true,
                navigateToAllTransactions = {},
                paddingValues = PaddingValues(),
            )
        }
    }
}
