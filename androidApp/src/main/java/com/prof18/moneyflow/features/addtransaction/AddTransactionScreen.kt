package com.prof18.moneyflow.features.addtransaction

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.prof18.moneyflow.R
import com.prof18.moneyflow.features.addtransaction.components.DatePickerDialog
import com.prof18.moneyflow.features.addtransaction.components.IconTextClickableRow
import com.prof18.moneyflow.features.addtransaction.components.MFTextInput
import com.prof18.moneyflow.features.addtransaction.components.TransactionTypeTabBar
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.style.AppMargins
import org.koin.androidx.compose.getViewModel

@Composable
fun AddTransactionScreen(
    categoryName: String?,
    categoryId: Long?,
    @DrawableRes categoryIcon: Int?,
    navigateUp: () -> Unit,
    navigateToCategoryList: () -> Unit
) {

    // TODO: do not use the view model like this! But extract outside and pass only the needed data!
    val viewModel = getViewModel<AddTransactionViewModel>()

    val (showDatePickerDialog, setShowedDatePickerDialog) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MFTopBar(
                topAppBarText = "Add transaction",
                actionTitle = "Save",
                onBackPressed = { navigateUp() },
                onActionClicked = {
                    viewModel.addTransaction(categoryId!!)
                    navigateUp()
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
                    onClick = { navigateToCategoryList() },
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

