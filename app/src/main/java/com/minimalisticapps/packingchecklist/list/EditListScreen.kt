package com.minimalisticapps.packingchecklist.list

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.minimalisticapps.packingchecklist.ConfirmationDialog
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.R
import com.minimalisticapps.packingchecklist.Screen
import com.minimalisticapps.packingchecklist.theme.PrimaryColorLight
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun EditListScreen(itemId: UUID?, navController: NavHostController) {
    val mContext = LocalContext.current as Activity
    val viewModel = getViewModel<MainViewModel>()

    val list =
        itemId?.let { viewModel.getList(it).observeAsState().value }

    if (itemId != null && list == null) {
        return
    }

    var text by remember {
        val initText = list?.list?.name ?: ""

        mutableStateOf(
            TextFieldValue(
                text = initText,
                selection = TextRange(initText.length, initText.length)
            )
        )
    }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    if (showConfirmationDialog) {
        ConfirmationDialog(
            text = mContext.getString(R.string.delete_list_confirmation),
            onPositiveClick = {
                showConfirmationDialog = false
                if (list != null) {
                    viewModel.deleteList(list.list)
                }
                navController.navigate(Screen.Lists.route)
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
                        if (isValid && list != null) {
                            viewModel.renameList(list.list, text.text)
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    isError = !isValid
                )
            }
            if (itemId != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(48.dp)
                        .padding(4.dp),
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
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(48.dp)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    enabled = isValid,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        if (text.text == "") return@Button

                        if (itemId == null) {
                            viewModel.addList(text.text)
                        }
                        navController.navigate(Screen.Lists.route)
                    },
                    content = {
                        Icon(
                            imageVector = if (itemId == null) Icons.Default.Done else Icons.Default.ArrowBack,
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
}
