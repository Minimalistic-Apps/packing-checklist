package com.minimalisticapps.packingchecklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minimalisticapps.packingchecklist.state.item.Item
import com.minimalisticapps.packingchecklist.ui.EnhancedInputText

@Composable
fun ItemsScreen(
    items: List<Item>,
    currentlyEditing: Item?,
    onAddItem: (Item) -> Unit,
    onRemoveItem: (Item) -> Unit,
    onStartEdit: (Item) -> Unit,
    onEditItemChange: (Item) -> Unit,
    onEditDone: () -> Unit
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { onAddItem(Item()) }, content = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add new Item",
                tint = MaterialTheme.colors.surface,
            )
        }, backgroundColor = MaterialTheme.colors.secondary)
    }) {
        Column() {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(top = 8.dp),
            ) {
                items(items = items) { todo ->
                    ItemRow(
                        isEditing = currentlyEditing?.id == todo.id,
                        item = todo,
                        onEditDone = onEditDone,
                        onEditItemChange = onEditItemChange,
                        onItemPressed = { onRemoveItem(it) },
                        onItemClicked = { onStartEdit(it) },
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRow(
    isEditing: Boolean,
    item: Item,
    onItemPressed: (Item) -> Unit,
    onItemClicked: (Item) -> Unit,
    onEditItemChange: (Item) -> Unit,
    onEditDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .combinedClickable(
                onClick = { onItemClicked(item) },
                onLongClick = { onItemPressed(item) }
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(if (isEditing) 64.dp else 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
        }
        Column {
            if (isEditing) {
                EnhancedInputText(
                    text = item.name,
                    onTextChange = { onEditItemChange(item.copy(name = it)) },
                    onImeAction = onEditDone,
                    placeholder = "Name of your item to pack"
                )
            } else {
                Text(text = item.name, modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}
