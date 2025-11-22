package com.prof18.moneyflow.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp),
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
            null
        },
        actions = {
            if (onActionClicked != null) {
                Spacer(modifier = Modifier.width(68.dp))
                TextButton(onClick = onActionClicked, enabled = actionEnabled) {
                    Text(
                        actionTitle!!.uppercase(),
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
    )
}

@Preview(name = "AddTransactionTopBar Light")
@Preview(name = "AddTransactionTopBar Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
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
