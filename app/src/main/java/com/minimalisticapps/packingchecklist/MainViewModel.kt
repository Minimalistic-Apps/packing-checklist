package com.minimalisticapps.packingchecklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val dao: DatabaseDao) : ViewModel() {
    fun getAllItems(): LiveData<List<ItemWithLists>> = dao.getItemWithLists().asLiveData()

    fun addItem() {
        viewModelScope.launch {
            dao.insertItem(Item(itemId = UUID.randomUUID(), name = ""))
        }
    }

    fun renameItem(item: Item, name: String) {
        item.name = name
        viewModelScope.launch {
            dao.updateItem(item)
        }
    }

}
