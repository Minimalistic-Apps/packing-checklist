package com.minimalisticapps.packingchecklist

sealed class Screen(val route: String) {
    object Checklists : Screen("Checklists")

    object Items : Screen("Items")
    object EditItem : Screen("EditItem")

    object Lists : Screen("Lists")

}
