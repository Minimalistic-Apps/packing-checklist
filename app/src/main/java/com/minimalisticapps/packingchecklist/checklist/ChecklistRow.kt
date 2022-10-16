package com.minimalisticapps.packingchecklist.checklist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.minimalisticapps.packingchecklist.ChecklistWithListsAndItems
import com.minimalisticapps.packingchecklist.Screen
import com.minimalisticapps.packingchecklist.UiRow
import com.minimalisticapps.packingchecklist.list.ListTag

@Composable
fun ChecklistRow(checklist: ChecklistWithListsAndItems, navController: NavHostController) {
    val checklistId = checklist.checklist.checklistId

    val openDetail = {
        navController.navigate(Screen.EditChecklist.route + "/${checklistId}")
    }
    val openChecklist = {
        navController.navigate(Screen.Checklist.route + "/${checklistId}")
    }

    UiRow(
        onClick = openChecklist,
        onLongClick = openDetail,
        content = {
            Row() {
                Box(modifier = Modifier) {
                    Text(text = checklist.checklist.name)
                }
                Spacer(Modifier.weight(1f))
                Box {
                    Row {
                        checklist.lists.forEach { itemList ->
                            ListTag(itemList = itemList, short = true)
                        }
                    }
                }

            }
        }
    )
}
