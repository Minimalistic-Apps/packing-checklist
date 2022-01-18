package com.minimalisticapps.packingchecklist.state

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

internal interface WithId {
    val id: UUID
}

fun <T> parseIdBasedMapFromJson(
    jsonArray: JSONArray,
    parseT: (jsonObject: JSONObject) -> T
): Map<UUID, T> where T : WithId {
    val resultMap = mutableMapOf<UUID, T>()

    for (i in 0 until jsonArray.length()) {
        val t = parseT(jsonArray[i] as JSONObject)
        resultMap[t.id] = t
    }

    return resultMap
}

fun <T> convertIdBasedMapToJson(
    map: Map<UUID, T>,
    convertT: (t: T) -> JSONObject
): JSONArray {
    val resultArray = JSONArray()

    for ((id, value) in map) {
        resultArray.put(convertT(value))
    }

    return resultArray
}