package com.minimalisticapps.packingchecklist.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel

@Composable
fun ItemsScreen() {
    val viewModel = getViewModel<MainViewModel>()
    val items = viewModel.getAllItems().observeAsState()

    UiList(
        displayItems = items.value ?: listOf(),
        onDelete = { viewModel.deleteItem(it.item) },
        content = { ItemRow(it) }
    )
}
