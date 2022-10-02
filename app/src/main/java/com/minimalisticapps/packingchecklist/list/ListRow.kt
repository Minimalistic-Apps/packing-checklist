package com.minimalisticapps.packingchecklist.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minimalisticapps.ClickToEditText
import com.minimalisticapps.packingchecklist.ListWithItems
import com.minimalisticapps.packingchecklist.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ListRow(listWithItems: ListWithItems) {
    val viewModel = getViewModel<MainViewModel>()
    Column(
        modifier = Modifier
            .padding(horizontal = 18.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            ClickToEditText(
                text = listWithItems.list.name,
                isEditable = viewModel.itemListIdToEdit.value == listWithItems.list.listId,
                clicked = { viewModel.listRowClicked(listWithItems.list.listId) },
                onChange = { viewModel.renameList(listWithItems.list, it) },
                onEditDone = { viewModel.listRowDone(listWithItems.list.listId) }
            )
        }
    }
}
