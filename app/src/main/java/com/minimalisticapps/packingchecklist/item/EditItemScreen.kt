package com.minimalisticapps.packingchecklist.item

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun EditItemScreen(itemId: UUID) {
    val viewModel = getViewModel<MainViewModel>()
    val itemLists = viewModel.getAllLists().observeAsState()
    val itemToChangeLists = viewModel.getItemWithList(itemId).observeAsState().value


    if (itemToChangeLists == null) {
        Log.e("EditItemScreen", "Item $itemId not found")
        Text("")
        return
    }

    var text by remember { mutableStateOf(TextFieldValue(text = itemToChangeLists.item.name)) }


    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(horizontal = 18.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                value = text,
                onValueChange = {
                    text = it
                    viewModel.renameItem(itemToChangeLists.item, text.text)
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            UiList(
                displayItems = itemLists.value ?: listOf(),
                onDelete = null,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        val checked =
                            itemToChangeLists.lists.find { it2 -> it2.listId == it.list.listId } != null
                        Checkbox(
                            checked = checked,
                            modifier = Modifier.padding(16.dp),
                            onCheckedChange = { newChecked ->
                                viewModel.toggleItemAssignedToList(
                                    itemToChangeLists.item.itemId,
                                    it.list.listId,
                                    newChecked,
                                )
                            },
                        )
                        Text(
                            text = it.list.name,
                            modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        )
                    }
                },
                onMove = null,
            )
        }
    }
}
