package com.minimalisticapps.packingchecklist.list

import androidx.compose.runtime.Composable
import com.minimalisticapps.ClickToEditText
import com.minimalisticapps.packingchecklist.ItemWithLists
import com.minimalisticapps.packingchecklist.ListWithItems
import com.minimalisticapps.packingchecklist.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ListRow(listWithItems: ListWithItems) {
    val viewModel = getViewModel<MainViewModel>()

    ClickToEditText(
        text = listWithItems.list.name,
        isEditable = viewModel.itemIdToEdit.value == listWithItems.list.listId,
        clicked = { viewModel.listRowClicked(listWithItems.list.listId) },
        onChange = { viewModel.renameList(listWithItems.list, it) },
        onEditDone = { viewModel.listRowDone(listWithItems.list.listId) }
    )
}
