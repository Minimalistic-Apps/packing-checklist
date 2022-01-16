package com.minimalisticapps.packingchecklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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
    Column {
        val enableTopSection = currentlyEditing == null
        ItemInputBackground(elevate = enableTopSection) {
            if (enableTopSection) {
                ItemAddInput(onAddItem)
            } else {
                Text(
                    text = "Editing item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp),
        ) {
            items(items = items) { todo ->
                if (currentlyEditing?.id == todo.id) {
                    ItemInlineEditor(
                        item = currentlyEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                    )
                } else {
                    ItemRow(
                        item = todo,
                        onItemPressed = { onRemoveItem(it) },
                        onItemClicked = { onStartEdit(it) },
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun ItemInlineEditor(
    item: Item,
    onEditItemChange: (Item) -> Unit,
    onEditDone: () -> Unit,
) {
    ItemInput(
        text = item.name,
        onNameChange = { onEditItemChange(item.copy(name = it)) },
        submit = onEditDone,
        buttonSlot = {
            Row {
                val shrinkButtons = Modifier.widthIn(5.dp)
                TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                    Text(
                        "\uD83D\uDCBE", // floppy disk
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(30.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun ItemAddInput(onItemComplete: (Item) -> Unit) {
    val (name, onNameChange) = rememberSaveable { mutableStateOf("") }

    val submit = {
        if (name.isNotBlank()) {
            onItemComplete(Item(name = name))
            onNameChange("")
        }
    }

    ItemInput(
        text = name,
        onNameChange = onNameChange,
        submit = submit,
    ) {
        ItemAddButton(onClick = submit, enabled = name.isNotBlank())
    }
}

@Composable
fun ItemInput(
    text: String,
    onNameChange: (String) -> Unit,
    submit: () -> Unit,
    buttonSlot: @Composable () -> Unit,
) {
    Column {
        Row(
            Modifier
                .height(IntrinsicSize.Min)
        ) {
            ItemInputText(
                text = text,
                onTextChange = onNameChange,
                modifier = Modifier.weight(1f),
                onImeAction = submit
            )
            Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRow(
    item: Item,
    onItemPressed: (Item) -> Unit,
    onItemClicked: (Item) -> Unit,
    modifier: Modifier = Modifier,
) {
    // onItemPressed(item)

    Row(
        modifier
            .combinedClickable(
                onClick = { onItemClicked(item) },
                onLongClick = { onItemPressed(item) }
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name)
    }
}
