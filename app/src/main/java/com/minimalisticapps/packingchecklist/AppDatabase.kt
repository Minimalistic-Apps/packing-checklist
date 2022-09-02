package com.minimalisticapps.packingchecklist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Item::class, ItemList::class, Checklist::class, ListHasItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

}
