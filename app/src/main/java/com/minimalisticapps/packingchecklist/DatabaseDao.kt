package com.minimalisticapps.packingchecklist

import androidx.room.*

@Dao
interface DatabaseDao {
    @Transaction
    @Query("SELECT * FROM ItemList")
    fun getListsWithItems(): List<ListWithItems>

    @Transaction
    @Query("SELECT * FROM Item")
    fun getItemWithLists(): List<ItemWithLists>

}
