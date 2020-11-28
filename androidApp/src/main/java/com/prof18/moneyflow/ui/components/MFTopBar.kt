package com.prof18.moneyflow.ui.components

import androidx.compose.material.Text
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import java.util.*

@Composable
fun MFTopBar(
    topAppBarText: String,
    actionTitle: String,
    onBackPressed: () -> Unit,
    onActionClicked: () -> Unit,
    actionEnabled: Boolean = true,
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
            TextButton(onClick = onActionClicked, enabled = actionEnabled) {
                Text(
                    actionTitle.toUpperCase(Locale.getDefault()),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    )
}

@Preview
@Composable
fun AddTransactionTopBarPreview() {
    return MFTopBar(
        topAppBarText = "Title",
        actionTitle = "Save",
        onBackPressed = {},
        onActionClicked = {},
        actionEnabled = false
    )
}