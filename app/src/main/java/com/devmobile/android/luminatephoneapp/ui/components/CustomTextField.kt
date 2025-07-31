package com.devmobile.android.luminatephoneapp.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devmobile.android.luminatephoneapp.ui.theme.Typography
import com.devmobile.android.luminatephoneapp.ui.theme.light_selection_handle_colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String = "Type here",
    textStyle: TextStyle = Typography.bodyLarge,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val interaction = remember { MutableInteractionSource() }

    CompositionLocalProvider(LocalTextSelectionColors provides light_selection_handle_colors) {
        BasicTextField(
            enabled = true,
            modifier = modifier
                .indicatorLine(
                    enabled = false,
                    isError = false,
                    interactionSource = interaction,
                    colors = TextFieldDefaults.colors().copy(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                    unfocusedIndicatorLineThickness = 0.dp //to hide the indicator line
                )
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            decorationBox = decorationBox(
                value.text, placeholder, textStyle,
                leadingIcon, trailingIcon
            )
        )
    }
}

@Composable
private fun decorationBox(
    value: String,
    placeholder: String,
    textStyle: TextStyle,
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?
): @Composable (@Composable () -> Unit) -> Unit = { inner ->

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        leadingIcon?.invoke()

        Box(
            modifier = Modifier.weight(1f),
            propagateMinConstraints = true
        ) {

            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = textStyle.fontSize,
                    textAlign = textStyle.textAlign,
                    color = textStyle.color.copy(alpha = 0.5f)
                )
            }
            inner()
        }

        trailingIcon?.invoke()
    }
}
