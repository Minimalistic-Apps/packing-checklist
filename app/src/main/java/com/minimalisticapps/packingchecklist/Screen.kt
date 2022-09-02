package com.minimalisticapps.packingchecklist

sealed class Screen(val route: String) {
    object Checklists: Screen("Checklists")
    object Items: Screen("Items")
    object Lists: Screen("Lists")
}
