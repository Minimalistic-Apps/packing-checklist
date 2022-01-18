package com.minimalisticapps.packingchecklist.state.checklist

import com.minimalisticapps.packingchecklist.state.WithId
import org.json.JSONObject
import java.util.*

private const val ID = "id"
private const val NAME = "name"

data class Checklist(
    override val id: UUID = UUID.randomUUID(),
    val name: String = "",
): WithId {
}

fun parseCheckListFromJson(jsonChecklist: JSONObject): Checklist {
    val id = UUID.fromString(jsonChecklist.getString(ID))
    val name: String = jsonChecklist.getString(NAME)
    return Checklist(id, name)
}

fun convertChecklistToJson(item: Checklist): JSONObject {
    val json = JSONObject()
    json.put(ID, item.id)
    json.put(NAME, item.name)

    return json
}