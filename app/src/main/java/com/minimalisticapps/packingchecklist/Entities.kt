package com.minimalisticapps.packingchecklist

import androidx.room.*

@Entity
data class Item(
    @PrimaryKey(autoGenerate = false)
    var itemId: Long,
    var name: String,
)

@Entity
data class ItemList(
    @PrimaryKey(autoGenerate = false)
    var listId: Long,
    var order: Long,
    var name: String,
)


@Entity(primaryKeys = ["listId", "itemId"])
data class ListHasItem(
    val listId: Long,
    val itemId: Long
)

data class ListWithItems(
    @Embedded val list: ItemList,
    @Relation(
        parentColumn = "listId",
        entityColumn = "itemId",
        associateBy = Junction(ListHasItem::class)
    )
    val items: List<Item>
)

data class ItemWithLists(
    @Embedded val item: Item,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "listId",
        associateBy = Junction(ListHasItem::class)
    )
    val lists: List<ItemList>
)

@Entity
data class Checklist(
    @PrimaryKey(autoGenerate = false) var checklistId: Long,
    var name: String,
    var order: Long,
    var isFinished: Boolean,
)



