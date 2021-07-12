package com.prof18.moneyflow.features.addtransaction.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

// TODO: check padding bottom of the text
@Composable
fun MFTextInput(
    text: String,
    label: String?,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType,
    textStyle: TextStyle,
    leadingIcon: @Composable (() -> Unit)? = null
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
                Text(
                    text = label,
                    modifier = Modifier.alpha(0.5f),
                    style= textStyle
                )
            }
        },
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onTextChange(text)
                focusManager.clearFocus()
            },
        ),
    )
}

@Preview
@Composable
fun MFTextInputPreviewWithIcon() {
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
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun MFTextInputDarkWithIconPreview() {
    MoneyFlowTheme(darkTheme = true) {
        Surface {
            MFTextInput(
                text = "This is a text",
                label = null,
                onTextChange = { },
                keyboardType = KeyboardType.Text,
                textStyle = MaterialTheme.typography.body1,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun MFTextInputPreview() {
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

@Preview
@Composable
fun MFTextInputLabelPreview() {
    MoneyFlowTheme {
        Surface {
            MFTextInput(
                text = "",
                label = "This is a label",
                onTextChange = { },
                keyboardType = KeyboardType.Text,
                textStyle = MaterialTheme.typography.body1,
            )
        }
    }
}