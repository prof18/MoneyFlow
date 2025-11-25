package com.prof18.moneyflow.presentation.addtransaction.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.ui.tooling.preview.Preview
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.*
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

// TODO: check padding bottom of the text
@Composable
internal fun MFTextInput(
    text: String,
    label: String?,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType,
    textStyle: TextStyle,
    leadingIcon: @Composable (() -> Unit)? = null,
) {

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text,
        onValueChange = {
            onTextChange(it)
        },
        modifier = modifier,
        textStyle = textStyle,
        placeholder = {
            if (label != null) {
                @Suppress("MagicNumber")
                Text(
                    text = label,
                    modifier = Modifier.alpha(0.5f),
                    style = textStyle,
                )
            }
        },
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onTextChange(text)
                focusManager.clearFocus()
            },
        ),
    )
}

@Preview(name = "MFTextInputPreviewWithIcon Light")
@Composable
private fun MFTextInputPreviewWithIcon() {
    MoneyFlowTheme {
        Surface {
            MFTextInput(
                text = "This is a text",
                label = null,
                onTextChange = { },
                keyboardType = KeyboardType.Text,
                textStyle = MaterialTheme.typography.body1,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_edit),
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@Preview(name = "MFTextInputPreview Light")
@Composable
private fun MFTextInputPreview() {
    MoneyFlowTheme {
        Surface {
            MFTextInput(
                text = "This is a text",
                label = null,
                onTextChange = { },
                keyboardType = KeyboardType.Text,
                textStyle = MaterialTheme.typography.body1,
            )
        }
    }
}
