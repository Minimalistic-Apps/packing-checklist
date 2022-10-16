package com.minimalisticapps.packingchecklist.list

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minimalisticapps.packingchecklist.ClickToEditText
import com.minimalisticapps.packingchecklist.ListWithItems
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiRow
import org.koin.androidx.compose.getViewModel

@Composable
fun ListRow(listWithItems: ListWithItems) {
    val viewModel = getViewModel<MainViewModel>()

    UiRow(
        content = {
            ClickToEditText(
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                text = listWithItems.list.name,
                isEditable = viewModel.itemListIdToEdit.value == listWithItems.list.listId,
                clicked = { viewModel.listRowClicked(listWithItems.list.listId) },
                onChange = { viewModel.renameList(listWithItems.list, it) },
                onEditDone = { viewModel.listRowDone() }
            )
        }
    )
}
