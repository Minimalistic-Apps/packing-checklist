package com.minimalisticapps.packingchecklist.state

import com.minimalisticapps.packingchecklist.state.checklist.Checklist
import com.minimalisticapps.packingchecklist.state.checklist.convertChecklistToJson
import com.minimalisticapps.packingchecklist.state.checklist.parseCheckListFromJson
import com.minimalisticapps.packingchecklist.state.event.Event
import com.minimalisticapps.packingchecklist.state.event.convertEventToJson
import com.minimalisticapps.packingchecklist.state.event.parseEventFromJson
import com.minimalisticapps.packingchecklist.state.item.convertItemToJson
import com.minimalisticapps.packingchecklist.state.item.parseItemFromJson
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*
import com.minimalisticapps.packingchecklist.state.item.Item as Item

private const val ITEM_ID = "itemId"
private const val CHECKLIST_ID = "checklistId"

private fun parseItemHasChecklistFromJson(jsonArray: JSONArray): Map<UUID, UUID> {
    val resultMap = mutableMapOf<UUID, UUID>()

    for (i in 0 until jsonArray.length()) {
        val _object = jsonArray[i] as JSONObject

        val itemId = UUID.fromString(_object.getString(ITEM_ID))
        val checklistId = UUID.fromString(_object.getString(CHECKLIST_ID))
        resultMap[itemId] = checklistId
    }

    return resultMap
}

private fun convertItemHasChecklistTOJson(map: Map<UUID, UUID>): JSONArray {
    val jsonArray = JSONArray()
    for ((itemId, checklistId) in map) {
        val _object = JSONObject()
        _object.put(ITEM_ID, itemId)
        _object.put(CHECKLIST_ID, checklistId)
        jsonArray.put(_object)
    }

    return jsonArray
}

private const val ITEMS = "items"
private const val CHECKLISTS = "checklists"
private const val ITEM_HAS_CHECKLIST = "itemHasChecklist"
private const val EVENTS = "events"

data class State(
    val items: Map<UUID, Item> = hashMapOf(),
    val checklists: Map<UUID, Checklist> = hashMapOf(),
    val itemHasChecklist: Map<UUID, UUID> = hashMapOf(),
    val events: Map<UUID, Event> = hashMapOf(),
) {
}

fun parseStateFromJson(raw_state: String): State {
    val jsonState = JSONTokener(raw_state).nextValue() as JSONObject

    val jsonItems = jsonState.getJSONArray(ITEMS) as JSONArray
    val jsonChecklists = jsonState.getJSONArray(CHECKLISTS) as JSONArray
    val jsonItemHasChecklist = jsonState.getJSONArray(ITEM_HAS_CHECKLIST) as JSONArray
    val jsonEvents = jsonState.getJSONArray(EVENTS) as JSONArray

    return State(
        items = parseIdBasedMapFromJson(jsonItems) { parseItemFromJson(it) },
        checklists = parseIdBasedMapFromJson(jsonChecklists) { parseCheckListFromJson(it) },
        itemHasChecklist = parseItemHasChecklistFromJson(jsonItemHasChecklist),
        events = parseIdBasedMapFromJson(jsonEvents) { parseEventFromJson(it) },
    )
}

fun convertStateToJson(state: State): JSONObject {
    val jsonState = JSONObject()
    jsonState.put(ITEMS, convertIdBasedMapToJson(state.items) { convertItemToJson(it) })
    jsonState.put(
        CHECKLISTS,
        convertIdBasedMapToJson(state.checklists) { convertChecklistToJson(it) })
    jsonState.put(
        ITEM_HAS_CHECKLIST,
        convertItemHasChecklistTOJson(state.itemHasChecklist)
    )
    jsonState.put(EVENTS, convertIdBasedMapToJson(state.events) { convertEventToJson(it) })

    return jsonState
}

