package com.prof18.moneyflow.presentation.addtransaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.presentation.addtransaction.components.DatePickerDialog
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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Suppress("LongMethod", "LongParameterList") // TODO: reduce method length
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
    updateYear: (Int) -> Unit,
    updateMonth: (Int) -> Unit,
    updateDay: (Int) -> Unit,
    saveDate: () -> Unit,
    dateLabel: String?,
    addTransactionAction: AddTransactionAction?,
    resetAction: () -> Unit,
) {

    val (showDatePickerDialog, setShowedDatePickerDialog) = remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()
    addTransactionAction?.let {
        when (it) {
            is AddTransactionAction.GoBack -> {
                navigateUp()
                resetAction()
            }
            is AddTransactionAction.ShowError -> {
                val messageText = stringResource(
                    it.uiErrorMessage.message,
                    *it.uiErrorMessage.messageArgs.toTypedArray(),
                )
                val nerdText = if (it.uiErrorMessage.nerdMessageArgs.isNotEmpty()) {
                    stringResource(
                        it.uiErrorMessage.nerdMessage,
                        *it.uiErrorMessage.nerdMessageArgs.toTypedArray(),
                    )
                } else {
                    ""
                }
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    val message = buildString {
                        append(messageText)
                        if (nerdText.isNotBlank()) {
                            append("\n")
                            append(nerdText)
                        }
                    }
                    scaffoldState.snackbarHostState.showSnackbar(message)
                    resetAction()
                }
            }
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MFTopBar(
                topAppBarText = stringResource(Res.string.add_transaction_screen),
                actionTitle = stringResource(Res.string.save),
                onBackPressed = { navigateUp() },
                onActionClicked = {
                    keyboardController?.hide()
                    addTransaction(categoryState.value?.id!!)
                },
                actionEnabled = categoryState.value?.id != null && amountText.isNotEmpty(),
            )
        },
        content = {
            Column {
                DatePickerDialog(
                    showDatePickerDialog,
                    setShowedDatePickerDialog,
                    onYearSelected = { updateYear(it) },
                    onMonthSelected = { updateMonth(it) },
                    onDaySelected = { updateDay(it) },
                    onSave = { saveDate() },
                )

                TransactionTypeTabBar(
                    transactionType = selectedTransactionType,
                    onTabSelected = { updateTransactionType(it) },
                    modifier = Modifier
                        .padding(Margins.regular),
                )

                MFTextInput(
                    text = amountText,
                    textStyle = MaterialTheme.typography.body1,
                    // TODO: inject user currency
                    label = "0.00 EUR",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_money_bill_wave),
                            contentDescription = null,
                        )
                    },
                    onTextChange = { updateAmountText(it) },
                    keyboardType = KeyboardType.Number,
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
                    textStyle = MaterialTheme.typography.body1,
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
                updateYear = {},
                updateMonth = {},
                updateDay = {},
                saveDate = {},
                dateLabel = "11 July 2021",
                addTransactionAction = null,
                resetAction = {},
            )
        }
    }
}
