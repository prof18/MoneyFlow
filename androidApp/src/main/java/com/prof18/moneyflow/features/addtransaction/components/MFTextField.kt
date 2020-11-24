package com.prof18.moneyflow.features.addtransaction.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType


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

    TextField(
        value = text,
        onValueChange = { onTextChange(it) },
        modifier = modifier,
        textStyle = textStyle,
        placeholder = {
            if (label != null) {
                Text(
                    text = label,
                    style = textStyle,
                    color = Color.Black.copy(alpha = 0.5f)
                )
            }
        },
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        onImeActionPerformed = { action, softKeyboardController ->
            if (action == ImeAction.Done) {
                onTextChange(text)
                softKeyboardController?.hideSoftwareKeyboard()
            }
        },
        activeColor = MaterialTheme.colors.onSurface,
        backgroundColor = Color.Transparent,
    )
}