package com.minimalisticapps.packingchecklist.item

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimalisticapps.packingchecklist.ItemWithLists
import com.minimalisticapps.packingchecklist.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ItemRow(itemWithLists: ItemWithLists) {
    val focusRequester = remember { FocusRequester() }
    val viewModel = getViewModel<MainViewModel>()

    var isEditable by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val rowModifier = Modifier.fillMaxWidth()

    Row(
        modifier = (
                if (!isEditable)
                    rowModifier.clickable(onClick = { isEditable = true })
                else
                    rowModifier
                )
            .padding(vertical = 14.dp, horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isEditable) {
            DisposableEffect(Unit) {
                focusRequester.requestFocus()
                onDispose { }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
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
                value = itemWithLists.item.name,
                onValueChange = { viewModel.renameItem(itemWithLists.item, it) },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            )
        }
    }
}
