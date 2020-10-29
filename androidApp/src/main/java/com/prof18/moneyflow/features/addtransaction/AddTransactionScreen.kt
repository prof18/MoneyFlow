package com.prof18.moneyflow.features.addtransaction

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prof18.moneyflow.R
import com.prof18.moneyflow.features.addtransaction.components.*
import com.prof18.moneyflow.features.addtransaction.data.TransactionTypeRadioItem
import com.prof18.moneyflow.ui.style.AppMargins
import java.util.*

@Composable
fun AddTransactionScreen(
    navController: NavController
) {

    val (showDialog, setShowedDialog) = remember { mutableStateOf(false) }

    // TODO: pass data from the viewModel
    Scaffold(
        topBar = {
            AddTransactionTopBar(
                topAppBarText = "Add transaction",
                actionTitle = "Save",
                onBackPressed = {
                    navController.popBackStack()
                },
                onSavePressed = {
                    // TODO
                },
                // TODO
                actionEnabled = false
            )
        },
        bodyContent = {
            Column() {
                DatePickerDialog(showDialog, setShowedDialog)
                TransactionTypeChooser(
                    possibleAnswerStringId = listOf(
                        TransactionTypeRadioItem(R.string.transaction_type_income),
                        TransactionTypeRadioItem(R.string.transaction_type_outcome)
                    ),
                    answer = TransactionTypeRadioItem(R.string.transaction_type_outcome),
                    onAnswerSelected = {
                        // TODO
                    },
                    modifier = Modifier.padding(
                        start = AppMargins.regular,
                        end = AppMargins.regular,
                        top = AppMargins.regular
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        start = AppMargins.regular,
                        end = AppMargins.regular,
                        top = AppMargins.regular
                    )

                ) {
                    Text(
                        "€",
                        style = MaterialTheme.typography.h4,
                    )

                    Spacer(Modifier.preferredWidth(AppMargins.small))

                    MFTextInput(
                        text = "test",
                        textStyle = MaterialTheme.typography.h3,
                        label = "0.00",
                        onTextChange = {
                            // TODO
                        },
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                IconTextClickableRow(
                    onClick = {
                        // TODO
                    },
                    text = "Select Category",
                    iconId = R.drawable.ic_question_circle,
                    isSomethingSelected = false,
                    modifier = Modifier.padding(
                        start = AppMargins.regular,
                        end = AppMargins.regular,
                        top = AppMargins.medium
                    )
                )

                IconTextClickableRow(
                    onClick = {
                        // TODO

                        setShowedDialog(true)


                    },
                    text = "Today",
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



/*

TODO: Add a view model

val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPosition)


    var todoItems: List<TodoItem> by mutableStateOf(listOf())
        private set

 */

//@Preview
//@Composable
//fun AddTransactionScreenPreview() {
//    return AddTransactionScreen()
//}