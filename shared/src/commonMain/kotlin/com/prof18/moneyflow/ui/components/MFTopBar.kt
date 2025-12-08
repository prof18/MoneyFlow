package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MFTopBar(
    topAppBarText: String,
    actionTitle: String? = null,
    onBackPressed: (() -> Unit)? = null,
    onActionClicked: (() -> Unit)? = null,
    actionEnabled: Boolean = true,
) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
            )
        },
        navigationIcon = if (onBackPressed != null) {
            {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = null,
                    )
                }
            }
        } else {
            {}
        },
        actions = {
            if (onActionClicked != null) {
                Spacer(modifier = Modifier.width(68.dp))
                TextButton(onClick = onActionClicked, enabled = actionEnabled) {
                    Text(
                        actionTitle!!.uppercase(),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}

@Preview(name = "AddTransactionTopBar Light")
@Composable
private fun AddTransactionTopBarPreview() {
    return MFTopBar(
        topAppBarText = "Title",
        actionTitle = "Save",
        onBackPressed = { },
        onActionClicked = { },
        actionEnabled = false,
    )
}
