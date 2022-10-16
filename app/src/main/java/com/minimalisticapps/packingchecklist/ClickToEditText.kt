package com.minimalisticapps.packingchecklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.delay

@Composable
fun ClickToEditText(
    text: String,
    isEditable: Boolean,
    clicked: () -> Unit,
    onChange: (text: String) -> Unit,
    onEditDone: () -> Unit,
    modifier: Modifier? = null,
) {
    val focusRequester = remember { FocusRequester() }

    var inputText by remember {
        val end = text.length
        mutableStateOf(
            TextFieldValue(
                text,
                selection = TextRange(start = end, end = end)
            )
        )
    }
    val rowModifier = (modifier ?: Modifier).fillMaxWidth()

    Row(
        modifier = (
                if (!isEditable)
                    rowModifier.clickable(onClick = {
                        clicked()

                        // Set cursor to end
                        val end = inputText.text.length
                        inputText = TextFieldValue(
                            text = inputText.text,
                            selection = TextRange(start = end, end = end)
                        )
                    })
                else
                    rowModifier
                )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isEditable) {
            // This is restarted with every recompose, recompose is run with every text change
            LaunchedEffect(key1 = inputText, block = {
                if (inputText.text.isBlank()) return@LaunchedEffect
                delay(200)
                onChange(inputText.text)
            })

            DisposableEffect(Unit) {
                focusRequester.requestFocus()
                onDispose { }
            }

            CustomTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onEditDone()
                }),
                value = inputText,
                onValueChange = { inputText = it },
            )
        } else {
            Text(text = text)
        }
    }
}
