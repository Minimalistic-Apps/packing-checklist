package com.minimalisticapps.packingchecklist

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

object UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID {
        return UUID.fromString(string)
    }
}

@Database(
    entities = [
        Item::class,
        ItemList::class,
        Checklist::class,
        ListHasItem::class,
        CheckListHasItem::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(UUIDConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

}
