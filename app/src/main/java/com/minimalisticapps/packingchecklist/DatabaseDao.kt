package com.minimalisticapps.packingchecklist

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

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

}
