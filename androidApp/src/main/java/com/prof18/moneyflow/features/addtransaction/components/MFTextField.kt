package com.prof18.moneyflow.features.addtransaction.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.prof18.moneyflow.ui.style.textColor


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
                    style = textStyle.copy(color = textStyle.color.copy(alpha = 0.8f))
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
//                softKeyboardController?.hideSoftwareKeyboard()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            textColor = textColor()
        )

       /* onImeActionPerformed = { action, softKeyboardController ->
            if (action == ImeAction.Done) {
                onTextChange(text)
                softKeyboardController?.hideSoftwareKeyboard()
            }
        },*/
//        activeColor = textColor(),
//        backgroundColor = Color.Transparent,
    )
}