@file:Suppress("MagicNumber")

package com.prof18.moneyflow.presentation.addtransaction.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.cancel
import money_flow.shared.generated.resources.confirm
import money_flow.shared.generated.resources.select_date
import org.jetbrains.compose.resources.stringResource

internal fun getYearList(): ImmutableList<String> = (2015..2023).map { it.toString() }.toPersistentList()
internal fun getMonthList(): ImmutableList<String> = (1..12).map { it.toString() }.toPersistentList()
internal fun getDayList(): ImmutableList<String> = (1..31).map { it.toString() }.toPersistentList()

// TODO: replace with a proper date picker
@Composable
internal fun DatePickerDialog(
    showDialog: Boolean,
    setDialogVisible: (Boolean) -> Unit,
    onYearSelected: (Int) -> Unit,
    onMonthSelected: (Int) -> Unit,
    onDaySelected: (Int) -> Unit,
    onSave: () -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                setDialogVisible(false)
            },
            title = {
                Text(text = stringResource(Res.string.select_date))
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    DatePickerItemDropdownMenu(
                        initialText = "Calendar.getInstance().get(Calendar.YEAR).toString()",
                        itemList = getYearList(),
                        onItemSelected = {
                            onYearSelected(it.toInt())
                        },
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    DatePickerItemDropdownMenu(
                        initialText = "(Calendar.getInstance().get(Calendar.MONTH) + 1).toString()",
                        itemList = getMonthList(),
                        onItemSelected = {
                            onMonthSelected(it.toInt())
                        },
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    DatePickerItemDropdownMenu(
                        initialText = "Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()",
                        itemList = getDayList(),
                        onItemSelected = {
                            onDaySelected(it.toInt())
                        },
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onSave()
                    setDialogVisible(false)
                }) {
                    Text(stringResource(Res.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    setDialogVisible(false)
                }) {
                    Text(stringResource(Res.string.cancel))
                }
            },
        )
    }
}

@Composable
private fun DatePickerItemDropdownMenu(
    initialText: String,
    itemList: ImmutableList<String>,
    onItemSelected: (String) -> Unit,
) {
    val (dropdownText, setDropdownText) = remember { mutableStateOf(initialText) }
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = {
                    expanded = true
                }),
        ) {
            Text(
                text = dropdownText,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            itemList.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    onClick = {
                        setDropdownText(it)
                        expanded = false
                        onItemSelected(it)
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun DatePickerDialogPreview() {
    MoneyFlowTheme {
        Surface {
            DatePickerDialog(
                showDialog = true,
                setDialogVisible = { },
                onYearSelected = { },
                onMonthSelected = { },
                onDaySelected = { },
                onSave = {},
            )
        }
    }
}
