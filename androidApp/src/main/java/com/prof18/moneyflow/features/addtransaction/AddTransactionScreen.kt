package com.prof18.moneyflow.features.addtransaction

import android.annotation.SuppressLint
import android.content.res.Configuration
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prof18.moneyflow.ComposeNavigationFactory
import com.prof18.moneyflow.R
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.data.db.model.TransactionType
import com.prof18.moneyflow.features.addtransaction.components.DatePickerDialog
import com.prof18.moneyflow.features.addtransaction.components.IconTextClickableRow
import com.prof18.moneyflow.features.addtransaction.components.MFTextInput
import com.prof18.moneyflow.features.addtransaction.components.TransactionTypeTabBar
import com.prof18.moneyflow.features.categories.data.CategoryUIData
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionAction
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.koin.androidx.compose.getViewModel

internal class AddTransactionScreenFactory(private val categoryState: MutableState<CategoryUIData?>) :
    ComposeNavigationFactory {
    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.AddTransactionScreen.route) {
            val viewModel = getViewModel<AddTransactionViewModel>()

            AddTransactionScreen(
                categoryState = categoryState,
                navigateUp = { navController.popBackStack() },
                navigateToCategoryList = {
                    navController.navigate("${Screen.CategoriesScreen.route}/true")
                },
                addTransaction = { categoryId ->
                    viewModel.addTransaction(categoryId)
                },
                amountText = viewModel.amountText,
                updateAmountText = { amountText ->
                    viewModel.amountText = amountText
                },
                descriptionText = viewModel.descriptionText,
                updateDescriptionText = { descText ->
                    viewModel.descriptionText = descText
                },
                selectedTransactionType = viewModel.selectedTransactionType,
                updateTransactionType = { transactionType ->
                    viewModel.selectedTransactionType = transactionType
                },
                updateYear = { year -> viewModel.setYearNumber(year) },
                updateMonth = { month -> viewModel.setMonthNumber(month) },
                updateDay = { day -> viewModel.setDayNumber(day) },
                saveDate = { viewModel.saveDate() },
                dateLabel = viewModel.dateLabel,
                addTransactionAction = viewModel.addTransactionAction,
                resetAction = { viewModel.resetAction() },
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    val uiErrorMessage = it.uiErrorMessage
                    val message = "${uiErrorMessage.message}\n${uiErrorMessage.nerdMessage}"
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
                topAppBarText = stringResource(id = R.string.add_transaction_screen),
                actionTitle = stringResource(R.string.save),
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
                            painter = painterResource(id = R.drawable.ic_money_bill_wave),
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
                    label = stringResource(id = R.string.description),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
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
                        ?: stringResource(id = R.string.select_category),
                    iconId = categoryState.value?.icon ?: R.drawable.ic_question_circle,
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
                    text = dateLabel ?: stringResource(id = R.string.today),
                    iconId = R.drawable.ic_calendar,
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
@Preview(name = "Add Transaction Screen Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
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
                            icon = R.drawable.ic_hamburger_solid,
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
