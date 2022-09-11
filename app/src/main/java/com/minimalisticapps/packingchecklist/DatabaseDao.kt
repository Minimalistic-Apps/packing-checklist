package com.minimalisticapps.packingchecklist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Transaction
    @Query("SELECT * FROM ItemList")
    fun getListsWithItems(): Flow<List<ListWithItems>>

    @Transaction
    @Query("SELECT * FROM Item")
    fun getItemWithLists(): Flow<List<ItemWithLists>>

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

}
