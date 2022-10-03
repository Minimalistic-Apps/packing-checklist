package com.minimalisticapps.packingchecklist.checklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.minimalisticapps.packingchecklist.Checklist
import com.minimalisticapps.packingchecklist.Screen

@Composable
fun ChecklistRow(checklist: Checklist, navController: NavHostController) {
    val openDetail = {
        navController.navigate(Screen.EditChecklist.route + "/${checklist.checklistId}")
    }

    Column(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 18.dp, end = 18.dp)
            .clickable { openDetail() }
    ) {
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
}
