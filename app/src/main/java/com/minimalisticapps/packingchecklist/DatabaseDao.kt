package com.minimalisticapps.packingchecklist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Transaction
    @Query("SELECT * FROM ItemList ORDER BY `ItemList`.`order`")
    fun getListsWithItems(): Flow<List<ListWithItems>>

    @Transaction
    @Query("SELECT * FROM Item ORDER BY `Item`.`order`")
    fun getItemWithLists(): Flow<List<ItemWithLists>>

    @Transaction
    @Query("SELECT * FROM Item WHERE Item.itemId = :itemId")
    fun getItemWithList(itemId: String): Flow<ItemWithLists>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Update
    suspend fun updateList(list: ItemList)

    @Delete
    suspend fun deleteList(list: ItemList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(itemList: ItemList)

    @Transaction
    suspend fun updateMultipleItems(items: List<Item>) {
        items.forEach { updateItem(it) }
    }

    @Transaction
    suspend fun updateMultipleLists(lists: List<ItemList>) {
        lists.forEach { updateList(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListHasItem(listHasItem: ListHasItem)

    @Delete
    suspend fun deleteListHasItem(listHasItem: ListHasItem)

    @Transaction
    @Query("SELECT * FROM Checklist WHERE Checklist.checklistId = :checklistId")
    fun getChecklistWithListsAndItems(checklistId: String): Flow<ChecklistWithListsAndItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklist(checklist: Checklist)

    @Update
    suspend fun updateChecklist(checklist: Checklist)

    @Delete
    suspend fun deleteChecklist(checklist: Checklist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklistHasList(checklistHasList: ChecklistHasList)

    @Delete
    suspend fun deleteChecklistHasList(checklistHasList: ChecklistHasList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklistHasItem(checklistHasItem: ChecklistHasItem)

    @Update
    suspend fun updateChecklistHasItem(checklistHasItem: ChecklistHasItem)

    @Delete
    suspend fun deleteChecklistHasItem(checklistHasItem: ChecklistHasItem)

    @Transaction
    @Query("SELECT * FROM Checklist ORDER BY `Checklist`.`order`")
    fun getChecklists(): Flow<List<ChecklistWithListsAndItems>>
}
