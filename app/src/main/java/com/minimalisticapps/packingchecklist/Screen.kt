package com.minimalisticapps.packingchecklist

sealed class Screen(val route: String) {
    object Checklists : Screen("Checklists")

    object Items : Screen("Items")
    object AddItem : Screen("AddItem")

    object Lists : Screen("Lists")
    object AddList : Screen("AddList")

}
