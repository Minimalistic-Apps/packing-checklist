package com.minimalisticapps.packingchecklist.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel

@Composable
fun ListsScreen() {
    val viewModel = getViewModel<MainViewModel>()
    val items = viewModel.getAllLists().observeAsState()

    UiList(
        displayItems = items.value ?: listOf(),
        onDelete = { viewModel.deleteList(it.list) },
        content = { ListRow(it) },
        onMove = { fromIndex, toIndex ->
            val from = items.value?.get(fromIndex)
            val to = items.value?.get(toIndex)

            if (from != null && to != null) {
                viewModel.reorderLists(from.list, to.list)
            }
        },
        height = 60.dp
    )
}
