package com.prof18.moneyflow.features.addtransaction.components

import androidx.compose.material.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonConstants
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.features.addtransaction.data.TransactionTypeRadioItem
import data.db.model.TransactionType

@Composable
fun TransactionTypeChooser(
    possibleAnswerStringId: List<TransactionTypeRadioItem>,
    answer: TransactionTypeRadioItem?,
    onAnswerSelected: (TransactionTypeRadioItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = possibleAnswerStringId.associateBy { stringResource(id = it.transactionTypeLabel) }

    val radioOptions = options.keys.toList()

    val selected = if (answer != null) {
        stringResource(id = answer.transactionTypeLabel)
    } else {
        null
    }

    val (selectedOption, onOptionSelected) = remember(answer) { mutableStateOf(selected) }

    Column(
        modifier = modifier
    ) {
        radioOptions.forEach { text ->
            val onClickHandle = {
                onOptionSelected(text)
                options[text]?.let { onAnswerSelected(it) }
                Unit
            }
            val optionSelected = text == selectedOption
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = optionSelected,
                        onClick = onClickHandle
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = optionSelected,
                    onClick = onClickHandle,
                    colors = RadioButtonConstants.defaultColors(
                        selectedColor = MaterialTheme.colors.primary
                    )
                )

                Text(
                    text = text,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Preview
@Composable
fun TransactionTypeChooserPreview() {
    return TransactionTypeChooser(
        possibleAnswerStringId = listOf(
            TransactionTypeRadioItem(R.string.transaction_type_income, TransactionType.INCOME),
            TransactionTypeRadioItem(R.string.transaction_type_outcome, TransactionType.OUTCOME)
        ),
        answer = null,
        onAnswerSelected = {},
    )
}