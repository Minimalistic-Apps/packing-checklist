package com.minimalisticapps.packingchecklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis
import java.util.*

class MainViewModel(private val dao: DatabaseDao) : ViewModel() {

    fun getAllItems(): LiveData<List<ItemWithLists>> = dao.getItemWithLists().asLiveData()

    fun addItem(name: String, lists: Map<UUID, Boolean>): Item {
        val item = Item(
            itemId = UUID.randomUUID(),
            name = name,
            order = currentTimeMillis(),
        )
        viewModelScope.launch {
            dao.insertItem(item)
            lists.forEach {
                if (it.value) {
                    dao.insertListHasItem(ListHasItem(it.key, item.itemId))
                }
            }
        }

        return item
    }

    fun renameItem(item: Item, name: String) {
        item.name = name
        viewModelScope.launch {
            dao.updateItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            dao.deleteItem(item)
        }
    }

    fun getAllLists(): LiveData<List<ListWithItems>> = dao.getListsWithItems().asLiveData()

    fun renameList(list: ItemList, name: String) {
        list.name = name
        viewModelScope.launch {
            dao.updateList(list)
        }
    }

    fun deleteList(list: ItemList) {
        viewModelScope.launch {
            dao.deleteList(list)
        }
    }

    fun addList(name: String) {
        viewModelScope.launch {
            val itemList = ItemList(
                listId = UUID.randomUUID(),
                name = name,
                order = currentTimeMillis(),
            )
            dao.insertList(itemList)
        }
    }

    fun reorderItems(from: Item, to: Item) {
        viewModelScope.launch {
            val originalTo = to.order
            to.order = from.order
            from.order = originalTo

            dao.updateMultipleItems(listOf(from, to))
        }
    }

    fun reorderLists(from: ItemList, to: ItemList) {
        viewModelScope.launch {
            val originalTo = to.order
            to.order = from.order
            from.order = originalTo

            dao.updateMultipleLists(listOf(from, to))
        }
    }

    fun toggleItemAssignedToList(itemId: UUID, listId: UUID, checked: Boolean) {
        viewModelScope.launch {
            if (checked) {
                dao.insertListHasItem(ListHasItem(listId, itemId))
            } else {
                dao.deleteListHasItem(ListHasItem(listId, itemId))
            }
        }
    }

    fun getItemWithList(itemId: UUID): LiveData<ItemWithLists> =
        dao.getItemWithList(itemId.toString()).asLiveData()

    fun getList(itemId: UUID): LiveData<ListWithItems> =
        dao.getList(itemId.toString()).asLiveData()

    fun getChecklistWithListsAndItems(checklistId: UUID): LiveData<ChecklistWithListsAndItems> =
        dao.getChecklistWithListsAndItems(checklistId.toString()).asLiveData()

    fun renameChecklist(checklist: Checklist, name: String) {
        checklist.name = name
        viewModelScope.launch {
            dao.updateChecklist(checklist)
        }
    }

    fun deleteChecklist(checklist: ChecklistWithListsAndItems) {
        viewModelScope.launch {
            checklist.lists.forEach {
                dao.deleteChecklistHasList(
                    ChecklistHasList(
                        checklistId = checklist.checklist.checklistId,
                        listId = it.listId
                    )
                )
            }
            checklist.checklistHasItems.forEach {
                dao.deleteChecklistHasItem(it)
            }
            dao.deleteChecklist(checklist.checklist)
        }
    }

    fun getAllChecklists(): LiveData<List<ChecklistWithListsAndItems>> =
        dao.getChecklists().asLiveData()

    fun createChecklist(
        name: String,
        checkedLists: Map<UUID, Boolean>,
        listWithItems: List<ListWithItems>
    ) {
        val checklist = Checklist(
            checklistId = UUID.randomUUID(),
            name = name,
            order = currentTimeMillis(),
            isFinished = false
        )
        viewModelScope.launch {
            dao.insertChecklist(checklist)

            val itemsMap: MutableMap<UUID, Item> = mutableMapOf()

            checkedLists.forEach {
                if (it.value) {
                    dao.insertChecklistHasList(
                        ChecklistHasList(
                            checklistId = checklist.checklistId,
                            listId = it.key
                        )
                    )
                    val listWithItem = listWithItems.find { it2 -> it.key == it2.list.listId }

                    listWithItem?.items?.forEach { itItem ->
                        itemsMap[itItem.itemId] = itItem
                    }
                }
            }

            itemsMap.forEach {
                dao.insertChecklistHasItem(
                    ChecklistHasItem(
                        checklistId = checklist.checklistId,
                        item = it.value,
                        isChecked = false
                    )
                )
            }
        }
    }

    fun toggleChecklistItem(checklistHasItem: ChecklistHasItem) {
        viewModelScope.launch {
            dao.updateChecklistHasItem(checklistHasItem)
        }
    }
}
