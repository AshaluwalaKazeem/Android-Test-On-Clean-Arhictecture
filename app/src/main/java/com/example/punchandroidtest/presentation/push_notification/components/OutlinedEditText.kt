package com.example.punchandroidtest.presentation.push_notification.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun OutlinedEditText(value: String, onTextChanged: (value: String) -> Unit, maxInput: Int? = null, placeHolder: String, label: String, leadingIcon: ImageVector, iconContentDescription: String, focus: FocusManager,) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (maxInput != null) {
                if(it.length <= maxInput) onTextChanged(it)
            }else{
                onTextChanged(it)
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(text = placeHolder)
        },
        label = {
            Text(text = label)
        },
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = iconContentDescription)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
            }
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = MaterialTheme.shapes.large,
    )
}