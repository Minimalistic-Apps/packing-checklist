package com.minimalisticapps.packingchecklist

sealed class Screen(val route: String) {
    object Checklists : Screen("Checklists")

    object Items : Screen("Items")
    object EditItem : Screen("EditItem")
    object EditList : Screen("EditList")

    object Lists : Screen("Lists")

    object EditChecklist : Screen("EditChecklist")
    object Checklist : Screen("Checklist")
}
