package com.minimalisticapps.packingchecklist.checklist

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.minimalisticapps.ConfirmationDialog
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.R
import com.minimalisticapps.packingchecklist.Screen
import com.minimalisticapps.packingchecklist.UiList
import com.minimalisticapps.packingchecklist.theme.PrimaryColorLight
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun EditChecklistScreen(itemId: UUID?, navController: NavHostController) {
    val mContext = LocalContext.current as Activity
    val viewModel = getViewModel<MainViewModel>()

    val itemLists = viewModel.getAllLists().observeAsState()
    val checklistToChangeLists =
        itemId?.let { viewModel.getChecklistWithListsAndItems(it).observeAsState().value }

    if (itemId != null && checklistToChangeLists == null) {
        return
    }

    var text by remember {
        val initText = checklistToChangeLists?.checklist?.name ?: ""

        mutableStateOf(
            TextFieldValue(
                text = initText,
                selection = TextRange(initText.length, initText.length)
            )
        )
    }
    var checkedLists by remember {
        mutableStateOf(
            checklistToChangeLists?.lists?.associate { Pair(it.listId, true) } ?: mapOf()
        )
    }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    if (showConfirmationDialog) {
        ConfirmationDialog(
            text = mContext.getString(R.string.delete_item_confirmation),
            onPositiveClick = {
                showConfirmationDialog = false
                if (checklistToChangeLists != null) {
                    viewModel.deleteChecklist(checklistToChangeLists.checklist)
                }
                navController.navigate(Screen.Items.route)
            },
            onDismiss = {
                showConfirmationDialog = false
            }
        )
    }

    val isValid = text.text.trim() != ""

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }

    Column {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    value = text,
                    onValueChange = {
                        text = it
                        if (isValid && checklistToChangeLists != null) {
                            viewModel.renameChecklist(checklistToChangeLists.checklist, text.text)
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    isError = !isValid
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(48.dp)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                if (itemId != null) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                            .clickable { showConfirmationDialog = true }
                    )
                } else {
                    Button(
                        enabled = isValid,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            if (text.text == "") return@Button

                            itemLists.value?.let {
                                viewModel.createChecklist(name = text.text, checkedLists, it)
                                navController.navigate(Screen.Checklists.route)
                            }
                        },
                        content = {
                            Icon(
                                Icons.Default.Done,
                                contentDescription = "Done",
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp)
                                    .scale(4f)
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PrimaryColorLight
                        ),
                    )
                }
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
                        val checked = checkedLists[it.list.listId] ?: false

                        Checkbox(
                            checked = checked,
                            modifier = Modifier.padding(16.dp),
                            onCheckedChange = { newChecked ->
                                checkedLists = checkedLists.plus(Pair(it.list.listId, newChecked))
//                                if (checklistToChangeLists != null) {
//                                    viewModel.toggleItemAssignedToList(
//                                        checklistToChangeLists.item.itemId,
//                                        it.list.listId,
//                                        newChecked,
//                                    )
//                                }
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
