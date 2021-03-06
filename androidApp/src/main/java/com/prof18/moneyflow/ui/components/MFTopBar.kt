package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun MFTopBar(
    topAppBarText: String,
    actionTitle: String? = null,
    onBackPressed: (() -> Unit)? = null,
    onActionClicked: (() -> Unit)? = null ,
    actionEnabled: Boolean = true,
) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp)
            )
        },
        navigationIcon = if (onBackPressed != null) {
            {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            }
        } else {
            null
        },
        actions = {
            if (onActionClicked != null) {
                Spacer(modifier = Modifier.width(68.dp))
                TextButton(onClick = onActionClicked, enabled = actionEnabled) {
                    Text(
                        actionTitle!!.toUpperCase(Locale.getDefault()),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
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
        onBackPressed = {  },
        onActionClicked = {  },
        actionEnabled = false
    )
}