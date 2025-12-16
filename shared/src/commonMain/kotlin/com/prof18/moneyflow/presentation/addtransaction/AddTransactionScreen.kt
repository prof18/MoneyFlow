package com.prof18.moneyflow.presentation.addtransaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.domain.entities.CurrencyConfig
import com.prof18.moneyflow.presentation.addtransaction.components.IconTextClickableRow
import com.prof18.moneyflow.presentation.addtransaction.components.MFTextInput
import com.prof18.moneyflow.presentation.addtransaction.components.TransactionTypeTabBar
import com.prof18.moneyflow.presentation.categories.data.CategoryUIData
import com.prof18.moneyflow.presentation.categories.mapToDrawableResource
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.add_transaction_screen
import money_flow.shared.generated.resources.cancel
import money_flow.shared.generated.resources.confirm
import money_flow.shared.generated.resources.description
import money_flow.shared.generated.resources.ic_calendar
import money_flow.shared.generated.resources.ic_edit
import money_flow.shared.generated.resources.ic_money_bill_wave
import money_flow.shared.generated.resources.ic_question_circle
import money_flow.shared.generated.resources.save
import money_flow.shared.generated.resources.select_category
import money_flow.shared.generated.resources.today
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock

@Composable
@Suppress("LongMethod", "LongParameterList") // TODO: reduce method length
@OptIn(ExperimentalMaterial3Api::class)
internal fun AddTransactionScreen(
    categoryState: State<CategoryUIData?>,
    navigateUp: () -> Unit,
    navigateToCategoryList: () -> Unit,
    addTransaction: (Long) -> Unit,
    amountText: String,
    updateAmountText: (String) -> Unit,
    descriptionText: String?,
    updateDescriptionText: (String?) -> Unit,
    selectedTransactionType: TransactionType,
    updateTransactionType: (TransactionType) -> Unit,
    updateSelectedDate: (Long) -> Unit,
    dateLabel: String?,
    selectedDateMillis: Long,
    addTransactionAction: AddTransactionAction?,
    resetAction: () -> Unit,
    currencyConfig: CurrencyConfig?,
) {
    val (showDatePickerDialog, setShowedDatePickerDialog) = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)

    LaunchedEffect(selectedDateMillis) {
        datePickerState.selectedDateMillis = selectedDateMillis
    }

    val snackbarHostState = remember { SnackbarHostState() }
    addTransactionAction?.let {
        when (it) {
            is AddTransactionAction.GoBack -> {
                navigateUp()
                resetAction()
            }
            is AddTransactionAction.ShowError -> {
                val messageText = stringResource(it.uiErrorMessage.message)
                LaunchedEffect(snackbarHostState, resetAction) {
                    snackbarHostState.showSnackbar(messageText)
                    resetAction()
                }
            }
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val amountLabel = currencyConfig?.let {
        val decimalPart = if (it.decimalPlaces == 0) {
            ""
        } else {
            ".${"0".repeat(it.decimalPlaces)}"
        }
        "${it.symbol} 0$decimalPart"
    } ?: "€ 0.00"

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MFTopBar(
                topAppBarText = stringResource(Res.string.add_transaction_screen),
                actionTitle = stringResource(Res.string.save),
                onBackPressed = { navigateUp() },
                onActionClicked = {
                    keyboardController?.hide()
                    categoryState.value?.id?.let(addTransaction)
                },
                actionEnabled = categoryState.value?.id != null && amountText.isNotEmpty(),
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                if (showDatePickerDialog) {
                    DatePickerDialog(
                        onDismissRequest = { setShowedDatePickerDialog(false) },
                        confirmButton = {
                            TextButton(
                                enabled = datePickerState.selectedDateMillis != null,
                                onClick = {
                                    datePickerState.selectedDateMillis?.let { selectedDate ->
                                        updateSelectedDate(selectedDate)
                                    }
                                    setShowedDatePickerDialog(false)
                                },
                            ) {
                                Text(text = stringResource(Res.string.confirm))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { setShowedDatePickerDialog(false) }) {
                                Text(text = stringResource(Res.string.cancel))
                            }
                        },
                    ) {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false,
                            colors = DatePickerDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                        )
                    }
                }

                TransactionTypeTabBar(
                    transactionType = selectedTransactionType,
                    onTabSelected = { updateTransactionType(it) },
                    modifier = Modifier
                        .padding(Margins.regular),
                )

                MFTextInput(
                    text = amountText,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    label = amountLabel,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_money_bill_wave),
                            contentDescription = null,
                        )
                    },
                    onTextChange = { updateAmountText(it) },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Margins.regular,
                            end = Margins.regular,
                            top = Margins.small,
                        ),
                )

                MFTextInput(
                    text = descriptionText ?: "",
                    textStyle = MaterialTheme.typography.bodyLarge,
                    label = stringResource(Res.string.description),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_edit),
                            contentDescription = null,
                        )
                    },
                    onTextChange = { updateDescriptionText(it) },
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Margins.regular,
                            end = Margins.regular,
                            top = Margins.regular,
                        ),
                )

                IconTextClickableRow(
                    onClick = { navigateToCategoryList() },
                    text = categoryState.value?.name
                        ?: stringResource(Res.string.select_category),
                    icon = categoryState.value?.icon?.mapToDrawableResource()
                        ?: Res.drawable.ic_question_circle,
                    isSomethingSelected = categoryState.value?.name != null,
                    modifier = Modifier.padding(
                        start = Margins.regular,
                        end = Margins.regular,
                        top = Margins.medium,
                    ),
                )

                IconTextClickableRow(
                    onClick = {
                        setShowedDatePickerDialog(true)
                    },
                    text = dateLabel ?: stringResource(Res.string.today),
                    icon = Res.drawable.ic_calendar,
                    modifier = Modifier.padding(
                        start = Margins.regular,
                        end = Margins.regular,
                        top = Margins.medium,
                        bottom = Margins.regular,
                    ),
                )
            }
        },
    )
}

@Preview(name = "Add Transaction Screen Light")
@Composable
private fun AddTransactionScreenPreview() {
    MoneyFlowTheme {
        Surface {
            AddTransactionScreen(
                categoryState = remember {
                    mutableStateOf(
                        CategoryUIData(
                            id = 1,
                            name = "Food",
                            icon = CategoryIcon.IC_HAMBURGER_SOLID,
                        ),
                    )
                },
                navigateUp = {},
                navigateToCategoryList = {},
                addTransaction = {},
                amountText = "10.00",
                updateAmountText = {},
                descriptionText = "Pizza 🍕",
                updateDescriptionText = {},
                selectedTransactionType = TransactionType.OUTCOME,
                updateTransactionType = {},
                updateSelectedDate = {},
                dateLabel = "11 July 2021",
                selectedDateMillis = Clock.System.now().toEpochMilliseconds(),
                addTransactionAction = null,
                resetAction = {},
                currencyConfig = CurrencyConfig(
                    code = "EUR",
                    symbol = "€",
                    decimalPlaces = 2,
                ),
            )
        }
    }
}
