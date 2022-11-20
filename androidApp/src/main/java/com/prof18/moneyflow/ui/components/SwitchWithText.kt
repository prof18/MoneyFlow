package com.prof18.moneyflow.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
internal fun SwitchWithText(
    onSwitchChanged: (Boolean) -> Unit,
    switchStatus: Boolean,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.h6,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = Margins.regular),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        @Suppress("MagicNumber")
        Text(
            text = title,
            style = titleStyle,
            modifier = Modifier
                .weight(0.9f)
                .clickable { onSwitchChanged(switchStatus.not()) }
                .padding(Margins.regular),
        )

        @Suppress("MagicNumber")
        Switch(
            modifier = Modifier.weight(0.1f),
            checked = switchStatus,
            onCheckedChange = { onSwitchChanged(it) },
        )
    }
}

@Preview(name = "SwitchWithText Light")
@Preview(name = "SwitchWithText Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SwitchWithTextPreview() {
    MoneyFlowTheme {
        Surface {
            SwitchWithText(
                onSwitchChanged = {},
                switchStatus = true,
                title = "A super dupe preference",
            )
        }
    }
}
