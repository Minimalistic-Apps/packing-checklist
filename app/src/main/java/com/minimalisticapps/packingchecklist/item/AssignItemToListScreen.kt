package com.minimalisticapps.packingchecklist.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel

@Composable
fun AssignItemToListScreen() {
    val viewModel = getViewModel<MainViewModel>()
    val itemLists = viewModel.getAllLists().observeAsState()
    val itemToChangeLists = viewModel.itemToChangeLists.value

    UiList(
        displayItems = itemLists.value ?: listOf(),
        onDelete = null,
        content = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = itemToChangeLists?.lists?.find { it2 -> it2.listId == it.list.listId } != null,
                    modifier = Modifier.padding(16.dp),
                    onCheckedChange = {
                                      viewModel
                    },
                )
                Text(
                    text = it.list.name,
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                )
            }
        },
        onMove = null,
        height = 40.dp
    )
}
