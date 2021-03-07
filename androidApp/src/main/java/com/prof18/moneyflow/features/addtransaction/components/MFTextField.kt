package com.prof18.moneyflow.features.addtransaction.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
//                focusManager.moveFocus(FocusDirection.Down)
//                softKeyboardController?.hideSoftwareKeyboard()
            },
//
        ),
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = Color.Transparent,
//            textColor = textColor()
//        )

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