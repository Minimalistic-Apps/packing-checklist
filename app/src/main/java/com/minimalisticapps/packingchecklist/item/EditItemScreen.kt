package com.minimalisticapps.packingchecklist.item

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.minimalisticapps.ConfirmationDialog
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.R
import com.minimalisticapps.packingchecklist.Screen
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun EditItemScreen(itemId: UUID, navController: NavHostController) {
    val mContext = LocalContext.current as Activity
    val viewModel = getViewModel<MainViewModel>()

    val itemLists = viewModel.getAllLists().observeAsState()
    val itemToChangeLists = viewModel.getItemWithList(itemId).observeAsState().value

    if (itemToChangeLists == null) {
        Log.e("EditItemScreen", "Item $itemId not found")
        Text("")
        return
    }

    var text by remember { mutableStateOf(TextFieldValue(text = itemToChangeLists.item.name)) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    if (showConfirmationDialog) {
        ConfirmationDialog(
            text = mContext.getString(R.string.delete_item_confirmation),
            onPositiveClick = {
                showConfirmationDialog = false
                viewModel.deleteItem(itemToChangeLists.item)
                navController.navigate(Screen.Items.route)
            },
            onDismiss = {
                showConfirmationDialog = false
            }
        )
    }

    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3.0f)
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
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(48.dp)
                    .height(32.dp),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Icon",
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .clickable { showConfirmationDialog = true }
                )
            }
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
