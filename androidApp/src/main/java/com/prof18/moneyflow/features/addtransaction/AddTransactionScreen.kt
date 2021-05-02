package com.prof18.moneyflow.features.addtransaction

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.prof18.moneyflow.R
import com.prof18.moneyflow.Screen
import com.prof18.moneyflow.features.addtransaction.components.*
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.style.AppMargins
import org.koin.androidx.compose.getViewModel

@Composable
fun AddTransactionScreen(
    navController: NavController,
    categoryName: String?,
    categoryId: Long?,
    @DrawableRes categoryIcon: Int?
) {

    val viewModel = getViewModel<AddTransactionViewModel>()

    val (showDatePickerDialog, setShowedDatePickerDialog) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MFTopBar(
                topAppBarText = "Add transaction",
                actionTitle = "Save",
                onBackPressed = {
                    navController.popBackStack()
                },
                onActionClicked = {
                    viewModel.addTransaction(categoryId!!)
                    navController.popBackStack()
                },
                actionEnabled = categoryId != null && viewModel.amountText.isNotEmpty()
            )
        },
        content = {
            Column {
                DatePickerDialog(
                    showDatePickerDialog,
                    setShowedDatePickerDialog,
                    onYearSelected = {
                        viewModel.setYearNumber(it)
                    },
                    onMonthSelected = {
                        viewModel.setMonthNumber(it)
                    },
                    onDaySelected = {
                        viewModel.setDayNumber(it)
                    },
                    onSave = {
                        viewModel.saveDate()
                    },

                    )

                TransactionTypeTabBar(
                    transactionType = viewModel.selectedTransactionType,
                    onTabSelected = {
                        viewModel.selectedTransactionType = it
                    },
                    modifier = Modifier
                        .padding(AppMargins.regular)
                )

                MFTextInput(
                    text = viewModel.amountText,
                    textStyle = MaterialTheme.typography.body1,
                    // TODO: change based on the currency
                    label = "0.00 EUR",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_money_bill_wave),
                            contentDescription = null,
                        )
                    },
                    onTextChange = {
                        viewModel.amountText = it
                    },
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = AppMargins.regular,
                            end = AppMargins.regular,
                            top = AppMargins.small
                        )

                )

                MFTextInput(
                    text = viewModel.descriptionText ?: "",
                    textStyle = MaterialTheme.typography.body1,
                    label = stringResource(id = R.string.description),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = null,
                        )
                    },
                    onTextChange = {
                        viewModel.descriptionText = it
                    },
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = AppMargins.regular,
                            end = AppMargins.regular,
                            top = AppMargins.regular
                        )
                )

                IconTextClickableRow(
                    onClick = {
                        navController.navigate("${Screen.CategoriesScreen.name}/true")
                    },
                    text = categoryName ?: stringResource(id = R.string.select_category),
                    iconId = categoryIcon ?: R.drawable.ic_question_circle,
                    isSomethingSelected = categoryName != null,
                    modifier = Modifier.padding(
                        start = AppMargins.regular,
                        end = AppMargins.regular,
                        top = AppMargins.medium
                    )
                )

                IconTextClickableRow(
                    onClick = {
                        setShowedDatePickerDialog(true)
                    },
                    text = viewModel.dateLabel ?: stringResource(id = R.string.today),
                    iconId = R.drawable.ic_calendar,
                    modifier = Modifier.padding(
                        start = AppMargins.regular,
                        end = AppMargins.regular,
                        top = AppMargins.medium,
                        bottom = AppMargins.regular
                    )
                )
            }
        }
    )
}

