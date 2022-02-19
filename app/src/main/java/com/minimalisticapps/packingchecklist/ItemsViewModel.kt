package com.minimalisticapps.packingchecklist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.minimalisticapps.packingchecklist.state.item.Item

class ItemsViewModel : ViewModel() {

    private var currentEditPosition by mutableStateOf(-1)

    var todoItems = mutableStateListOf<Item>()
        private set

    val currentEditItem: Item?
        get() = todoItems.getOrNull(currentEditPosition)

    fun addItem(item: Item) {
        todoItems.add(item)
    }

    fun removeItem(item: Item) {
        todoItems.remove(item)
        onEditDone() // don't keep the editor open when removing items
    }

    fun onEditItemSelected(item: Item) {
        currentEditPosition = todoItems.indexOf(item)
    }

    fun onEditDone() {
        currentEditPosition = -1
    }

    fun onEditItemChange(item: Item) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }

        todoItems[currentEditPosition] = item
    }
}