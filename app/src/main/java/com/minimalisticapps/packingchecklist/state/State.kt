package com.minimalisticapps.packingchecklist.state

import java.util.*

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
    val itemsCheckedStatus: Map<UUID, Boolean> = hashMapOf(),
)

class State(
    items: Map<UUID, Item> = hashMapOf(),
    checklists: Map<UUID, Checklist> = hashMapOf(),
    itemHasChecklist: Map<UUID, UUID> = hashMapOf(),
    events: Map<UUID, Event> = hashMapOf(),
) {
}