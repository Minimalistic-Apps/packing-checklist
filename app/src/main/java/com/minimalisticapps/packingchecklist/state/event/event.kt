package com.minimalisticapps.packingchecklist.state.event

import com.minimalisticapps.packingchecklist.state.WithId
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

const val C_ID = "id"
const val CHECKED = "checked"


private fun parseItemsCheckedStatus(jsonStatuses: JSONArray): Map<UUID, Boolean> {
    val items = mutableMapOf<UUID, Boolean>()

    for (i in 0 until jsonStatuses.length()) {
        val jsonStatus = jsonStatuses.getJSONObject(i)
        val id = UUID.fromString(jsonStatus.getString(C_ID))
        items[id] = jsonStatus.getBoolean(CHECKED)
    }

    return items
}

private fun convertItemsChecksStatusToJson(itemsCheckedStatus: Map<UUID, Boolean>): JSONArray {
    val jsonArray = JSONArray()
    for ((id, isChecked) in itemsCheckedStatus) {
        val jsonCheckedStatus = JSONObject()
        jsonCheckedStatus.put(E_ID, id)
        jsonCheckedStatus.put(CHECKED, isChecked)
        jsonArray.put(jsonCheckedStatus)
    }

    return jsonArray
}


private const val E_ID = "id"
private const val NAME = "name"
private const val ITEMS_CHECKED_STATUS = "itemsCheckedStatus"
private const val CHECKLIST_ID = "checklistId"

data class Event(
    override val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val checklistId: UUID,
    val itemsCheckedStatus: Map<UUID, Boolean> = hashMapOf(),
): WithId {

}

fun parseEventFromJson(jsonEvent: JSONObject): Event {
    val id = UUID.fromString(jsonEvent.getString(E_ID))
    val name: String = jsonEvent.getString(NAME)
    val checklistId = UUID.fromString(jsonEvent.getString(CHECKLIST_ID))
    val itemsCheckedStatusJson = jsonEvent.getJSONArray(ITEMS_CHECKED_STATUS)
    val itemsCheckedStatus = parseItemsCheckedStatus(itemsCheckedStatusJson)

    return Event(id, name, checklistId, itemsCheckedStatus)
}

fun convertEventToJson(event: Event): JSONObject {
    val json = JSONObject()
    json.put(E_ID, event.id)
    json.put(NAME, event.name)
    json.put(CHECKLIST_ID, event.checklistId)
    json.put(ITEMS_CHECKED_STATUS, convertItemsChecksStatusToJson(event.itemsCheckedStatus))

    return json
}