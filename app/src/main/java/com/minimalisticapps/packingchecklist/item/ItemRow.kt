package com.minimalisticapps.packingchecklist.item

import androidx.compose.runtime.Composable
import com.minimalisticapps.ClickToEditText
import com.minimalisticapps.packingchecklist.ItemWithLists
import com.minimalisticapps.packingchecklist.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ItemRow(itemWithLists: ItemWithLists) {
    val viewModel = getViewModel<MainViewModel>()

    ClickToEditText(
        text = itemWithLists.item.name,
        isEditable = viewModel.itemIdToEdit.value == itemWithLists.item.itemId,
        clicked = { viewModel.itemRowClicked(itemWithLists.item.itemId) },
        onChange = { viewModel.renameItem(itemWithLists.item, it) },
        onEditDone = { viewModel.itemRowDone(itemWithLists.item.itemId) }
    )
}
