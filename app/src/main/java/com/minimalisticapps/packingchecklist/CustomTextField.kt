package com.minimalisticapps.packingchecklist

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.minimalisticapps.packingchecklist.theme.SurfaceColorForLight
import com.minimalisticapps.packingchecklist.theme.SurfaceColorForDark

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    val interactionSource = remember { MutableInteractionSource() }
    // parameters below will be passed to BasicTextField for correct behavior of the text field,
    // and to the decoration box for proper styling and sizing
    val enabled = true
    val singleLine = true
    val visualTransformation =
        VisualTransformation {
            TransformedText(
                text = it,
                offsetMapping = OffsetMapping.Identity
            )
        }

    val surfaceColor = if (isSystemInDarkTheme()) SurfaceColorForDark else SurfaceColorForLight
    val colors = TextFieldDefaults.textFieldColors(
        textColor = surfaceColor,
        cursorColor = surfaceColor,
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(
            color = colors.textColor(enabled).value,
        ),
        // Must be `surfaceColor` for some reason the error color is used instead
        cursorBrush = Brush.verticalGradient(listOf(surfaceColor, surfaceColor)),
        modifier = modifier
            .background(
                color = colors.backgroundColor(enabled).value,
                shape = TextFieldDefaults.TextFieldShape
            )
            .indicatorLine(
                enabled = enabled,
                isError = false,
                interactionSource = interactionSource,
                colors = colors
            ),
        // internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = value.text,
            visualTransformation = visualTransformation,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            // same interaction source as the one passed to BasicTextField to read focus state
            // for text field styling
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                start = 6.dp, end = 6.dp, top = 6.dp, bottom = 6.dp
            ),
            isError = false
        )
    }
}
