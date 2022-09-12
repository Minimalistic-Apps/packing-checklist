package com.minimalisticapps.packingchecklist.item

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.minimalisticapps.ClickToEditText
import com.minimalisticapps.packingchecklist.ItemWithLists
import com.minimalisticapps.packingchecklist.MainViewModel
import org.koin.androidx.compose.getViewModel

val NoPadding = PaddingValues(
    start = 0.dp,
    top = 0.dp,
    end = 0.dp,
    bottom = 0.dp
)

@Composable
fun ItemRow(itemWithLists: ItemWithLists) {
    val viewModel = getViewModel<MainViewModel>()

    Column(modifier = Modifier.padding(horizontal = 18.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            contentAlignment = Alignment.Center,
        ) {
            ClickToEditText(
                text = itemWithLists.item.name,
                isEditable = viewModel.itemIdToEdit.value == itemWithLists.item.itemId,
                clicked = { viewModel.itemRowClicked(itemWithLists.item.itemId) },
                onChange = { viewModel.renameItem(itemWithLists.item, it) },
                onEditDone = { viewModel.itemRowDone(itemWithLists.item.itemId) }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            contentAlignment = Alignment.CenterStart,
        ) {

            itemWithLists.lists.forEach {
                Button(
                    contentPadding = NoPadding,
                    modifier = Modifier.height(20.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = it.name)
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete",
                    )
                }
            }
            Button(
                onClick = { /*TODO*/ },
                contentPadding = NoPadding,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                )

            }
        }
    }
}
