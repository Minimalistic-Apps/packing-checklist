package com.minimalisticapps.packingchecklist.state.item

import com.minimalisticapps.packingchecklist.state.WithId
import org.json.JSONObject
import java.util.*

private const val ID = "id"
private const val NAME = "name"

data class Item(
    override val id: UUID = UUID.randomUUID(),
    val name: String = "",
): WithId {
}

fun parseItemFromJson(jsonObject: JSONObject): Item {
    val id = UUID.fromString(jsonObject.getString(ID))
    val name: String = jsonObject.getString(NAME)

    return Item(id = id, name = name)
}

fun convertItemToJson(item: Item): JSONObject {
    val json = JSONObject()
    json.put(ID, item.id)
    json.put(NAME, item.name)

    return json
}