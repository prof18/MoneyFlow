package com.prof18.moneyflow.features.addtransaction.components

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import java.util.*

@Composable
fun AddTransactionTopBar(
    topAppBarText: String,
    actionTitle: String,
    onBackPressed: () -> Unit,
    onSavePressed: () -> Unit,
    actionEnabled: Boolean
) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Filled.Close)
            }
        },
        actions = {
            Spacer(modifier = Modifier.preferredWidth(68.dp))
            TextButton(onClick = onSavePressed, enabled = actionEnabled) {
                Text(
                    actionTitle.toUpperCase(Locale.getDefault()),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    )
}

@Preview
@Composable
fun AddTransactionTopBarPreview() {
    return AddTransactionTopBar(
        topAppBarText = "Title",
        actionTitle = "Save",
        onBackPressed = {},
        onSavePressed = {},
        actionEnabled = false
    )
}