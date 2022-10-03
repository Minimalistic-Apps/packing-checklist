package com.minimalisticapps.packingchecklist.checklist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.minimalisticapps.packingchecklist.Checklist
import com.minimalisticapps.packingchecklist.Screen
import com.minimalisticapps.packingchecklist.UiRow

@Composable
fun ChecklistRow(checklist: Checklist, navController: NavHostController) {
    val openDetail = {
        navController.navigate(Screen.EditChecklist.route + "/${checklist.checklistId}")
    }

    UiRow(
        onClick = openDetail,
        content = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row() {
                    Text(
                        modifier = Modifier.weight(1.0f, fill = true),
                        text = checklist.name
                    )
                }
            }
        }
    )
}
