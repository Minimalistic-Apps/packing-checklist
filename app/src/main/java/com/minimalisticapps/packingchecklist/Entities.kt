package com.minimalisticapps.packingchecklist

import androidx.room.*
import java.util.*

interface HasKey {
    val key: String
}

@Entity
data class Item(
    @PrimaryKey(autoGenerate = false)
    var itemId: UUID,
    var name: String,
    var order: Long,
) : HasKey {
    override val key: String get() = itemId.toString()
}

@Entity
data class ItemList(
    @PrimaryKey(autoGenerate = false)
    var listId: UUID,
    var name: String,
    var order: Long,
) : HasKey {
    override val key: String get() = listId.toString()
}


@Entity(primaryKeys = ["listId", "itemId"])
data class ListHasItem(
    val listId: UUID,
    val itemId: UUID
) : HasKey {
    override val key: String get() = "${listId}___${itemId}"
}

data class ListWithItems(
    @Embedded val list: ItemList,
    @Relation(
        parentColumn = "listId",
        entityColumn = "itemId",
        associateBy = Junction(ListHasItem::class)
    )
    val items: List<Item>
) : HasKey {
    override val key: String get() = list.listId.toString()
}

data class ItemWithLists(
    @Embedded val item: Item,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "listId",
        associateBy = Junction(ListHasItem::class)
    )
    val lists: List<ItemList>
) : HasKey {
    override val key: String get() = item.itemId.toString()
}

@Entity
data class Checklist(
    @PrimaryKey(autoGenerate = false) var checklistId: UUID,
    var name: String,
    var order: Long,
    var isFinished: Boolean,
) : HasKey {
    override val key: String get() = checklistId.toString()
}

@Entity(primaryKeys = ["checklistId", "itemId"])
data class ChecklistHasItem(
    val checklistId: UUID,
    var isChecked: Boolean,
    @Embedded val item: Item,
) : HasKey {
    override val key: String get() = "${checklistId}___${item.itemId}"
}

@Entity(primaryKeys = ["checklistId", "listId"])
data class ChecklistHasList(
    val checklistId: UUID,
    val listId: UUID,
) : HasKey {
    override val key: String get() = "${checklistId}___${listId}"
}

data class ChecklistWithListsAndItems(
    @Embedded val checklist: Checklist,

    @Relation(
        parentColumn = "checklistId",
        entityColumn = "itemId",
        entity = ChecklistHasItem::class,
        associateBy = Junction(ChecklistHasItem::class)
    )
    val checklistHasItems: List<ChecklistHasItem>,

    @Relation(
        parentColumn = "checklistId",
        entityColumn = "listId",
        associateBy = Junction(ChecklistHasList::class)
    )
    val lists: List<ItemList>

) : HasKey {
    override val key: String get() = checklist.checklistId.toString()
}
