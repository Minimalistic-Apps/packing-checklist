package com.minimalisticapps.packingchecklist.checklist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.minimalisticapps.packingchecklist.Checklist
import com.minimalisticapps.packingchecklist.Screen
import com.minimalisticapps.packingchecklist.UiRow

@Composable
fun ChecklistRow(checklist: Checklist, navController: NavHostController) {
    val openDetail = {
        navController.navigate(Screen.EditChecklist.route + "/${checklist.checklistId}")
    }
    val openChecklist = {
        navController.navigate(Screen.Checklist.route + "/${checklist.checklistId}")
    }

    UiRow(
        onClick = openChecklist,
        onLongClick = openDetail,
        content = {
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                text = checklist.name
            )
        }
    )
}
