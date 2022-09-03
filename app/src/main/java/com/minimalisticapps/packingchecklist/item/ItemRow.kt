package com.minimalisticapps.packingchecklist.item

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimalisticapps.packingchecklist.ItemWithLists
import com.minimalisticapps.packingchecklist.MainViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun ItemRow(itemWithLists: ItemWithLists) {
    val focusRequester = remember { FocusRequester() }
    val viewModel = getViewModel<MainViewModel>()

    var inputText by remember {
        val end = itemWithLists.item.name.length
        mutableStateOf(
            TextFieldValue(
                itemWithLists.item.name,
                selection = TextRange(start = end, end = end)
            )
        )
    }
    var isEditable by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    // This is restarted with every recompose, recompose is run with every text change
    LaunchedEffect(key1 = inputText, block = {
        if (inputText.text.isBlank()) return@LaunchedEffect
        delay(200)
        viewModel.renameItem(itemWithLists.item, inputText.text)
    })

    val rowModifier = Modifier.fillMaxWidth()

    Row(
        modifier = (
                if (!isEditable)
                    rowModifier.clickable(onClick = {
                        isEditable = true

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
            .padding(horizontal = 18.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isEditable) {
            DisposableEffect(Unit) {
                focusRequester.requestFocus()
                onDispose { }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            isFocused = true
                        }

                        if (isFocused && !it.isFocused) {
                            isEditable = false
                            isFocused = false
                        }
                    },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    isEditable = false
                    isFocused = false
                }),
                value = inputText,
                onValueChange = { inputText = it },
                singleLine = true,
            )
        } else {
            Text(
                text = itemWithLists.item.name,
                style = MaterialTheme.typography.body1,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(8f)
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            )
        }
    }
}
