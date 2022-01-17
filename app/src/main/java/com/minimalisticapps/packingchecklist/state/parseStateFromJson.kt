package com.minimalisticapps.packingchecklist.state

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*

fun parseItemsFromJson(jsonItems: JSONArray): Map<UUID, Item> {
    val items = mutableMapOf<UUID, Item>()

    for (i in 0 until jsonItems.length()) {
        val jsonItem = jsonItems.getJSONObject(i)
        val id = UUID.fromString(jsonItem.getString("id"))
        val name: String = jsonItem.getString("name")
        items[id] = Item(id, name)
    }

    return items
}

fun parseCheckListsFromJson(jsonChecklists: JSONArray): Map<UUID, Checklist> {
    val items = mutableMapOf<UUID, Checklist>()

    for (i in 0 until jsonChecklists.length()) {
        val jsonChecklist = jsonChecklists.getJSONObject(i)
        val id = UUID.fromString(jsonChecklist.getString("id"))
        val name: String = jsonChecklist.getString("name")
        items[id] = Checklist(id, name)
    }

    return items
}

fun parseItemsCheckedStatus(jsonStatuses: JSONArray): Map<UUID, Boolean> {
    val items = mutableMapOf<UUID, Boolean>()

    for (i in 0 until jsonStatuses.length()) {
        val jsonStatus = jsonStatuses.getJSONObject(i)
        val id = UUID.fromString(jsonStatus.getString("id"))

        items[id] = jsonStatus.getBoolean("status")
    }

    return items
}

fun parseEventsFromJson(jsonEvents: JSONArray): Map<UUID, Event> {
    val items = mutableMapOf<UUID, Event>()

    for (i in 0 until jsonEvents.length()) {
        val jsonEvent = jsonEvents.getJSONObject(i)
        val id = UUID.fromString(jsonEvent.getString("id"))
        val name: String = jsonEvent.getString("name")
        val checklistId = UUID.fromString(jsonEvent.getString("checklistId"))
        val itemsCheckedStatusJson = jsonEvent.getJSONArray("itemsCheckedStatus")
        val itemsCheckedStatus = parseItemsCheckedStatus(itemsCheckedStatusJson)
        items[id] = Event(id, name, checklistId, itemsCheckedStatus)
    }

    return items
}

fun parseItemHasChecklistFromJson(jsonItems: JSONArray): Map<UUID, UUID> {
    val items = mutableMapOf<UUID, UUID>()

    for (i in 0 until jsonItems.length()) {
        val jsonItem = jsonItems.getJSONObject(i)
        val itemId = UUID.fromString(jsonItem.getString("itemId"))
        val checkListId = UUID.fromString(jsonItem.getString("checkListId"))
        items[itemId] = checkListId
    }

    return items
}

fun parseStateFromJson(raw_state: String): State {
    val jsonState = JSONTokener(raw_state).nextValue() as JSONObject

    val jsonItems = jsonState.getJSONArray("items") as JSONArray
    val jsonChecklists = jsonState.getJSONArray("checklists")
    val jsonItemHasChecklist = jsonState.getJSONArray("itemHasChecklist")


    return State(
        items = parseItemsFromJson(jsonItems),
        checklists = parseCheckListsFromJson(jsonChecklists),
        itemHasChecklist = parseItemHasChecklistFromJson(jsonItemHasChecklist),
        events = parseEventsFromJson(jsonItemHasChecklist),
    )
}