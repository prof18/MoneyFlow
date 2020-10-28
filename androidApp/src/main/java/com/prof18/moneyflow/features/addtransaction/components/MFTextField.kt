package com.prof18.moneyflow.features.addtransaction.components

import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    textStyle: TextStyle
) {

    // TODO: remove and move to external
    val (text1, updateText) = remember { mutableStateOf("") }


    TextField(
        value = text1,
        textStyle = textStyle,
        activeColor = MaterialTheme.colors.onSurface,
        onValueChange = { /*onTextChange(it)*/ updateText(it) },
        placeholder = {
            if (label != null) {
                Text(
                    text = label,
                    style = textStyle,
                    color = Color.Black.copy(alpha = 0.5f)
                )
            }
        },
        backgroundColor = Color.Transparent,
        imeAction = ImeAction.Done,
        onImeActionPerformed = { action, softKeyboardController ->
            if (action == ImeAction.Done) {
                onTextChange(text)
                softKeyboardController?.hideSoftwareKeyboard()
            }
        },
        keyboardType = keyboardType,
        modifier = modifier
    )

}