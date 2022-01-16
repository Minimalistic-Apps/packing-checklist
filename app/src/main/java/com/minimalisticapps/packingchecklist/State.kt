package com.minimalisticapps.packingchecklist

import java.util.*
import kotlin.collections.HashMap

data class Item(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
) {
}

data class Checklist(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
) {
}

data class Event(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val checklistId: UUID,
    val itemsCheckedStatus: HashMap<UUID, Boolean>,
)

class State(
    items: HashMap<UUID, Item>,
    checklists: HashMap<UUID, Checklist>,
    itemHasChecklist: HashMap<UUID, UUID>,
    events: HashMap<UUID, Event>
) {
}