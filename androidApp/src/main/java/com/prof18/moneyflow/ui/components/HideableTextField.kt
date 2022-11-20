package com.prof18.moneyflow.ui.components

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
internal fun HideableTextField(
    modifier: Modifier = Modifier,
    text: String,
    hide: Boolean,
    style: TextStyle = MaterialTheme.typography.body1,
) {

    val hiddenWord: String by remember { mutableStateOf(text.replace("[\\d|.,]".toRegex(), "*")) }
    Text(
        modifier = modifier,
        text = if (hide) {
            hiddenWord
        } else {
            text
        },
        style = style,
    )
}

@Preview(name = "HideableTextFieldVisible Light")
@Preview(name = "HideableTextFieldVisible Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HideableTextFieldVisiblePreview() {
    MoneyFlowTheme {
        Surface {
            HideableTextField(
                text = "$ 10.000", hide = true,
            )
        }
    }
}

@Preview(name = "HideableTextFieldHidden Light")
@Preview(name = "HideableTextFieldHidden Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HideableTextFieldHiddenPreview() {
    MoneyFlowTheme {
        Surface {
            HideableTextField(
                text = "$ 10.000", hide = false,
            )
        }
    }
}
