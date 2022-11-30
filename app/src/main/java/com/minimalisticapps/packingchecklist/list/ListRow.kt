package com.minimalisticapps.packingchecklist.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.minimalisticapps.packingchecklist.ListWithItems
import com.minimalisticapps.packingchecklist.Screen
import com.minimalisticapps.packingchecklist.UiRow

@Composable
fun ListRow(listWithItems: ListWithItems, navController: NavHostController) {
    val openDetail = {
        navController.navigate(Screen.EditList.route + "/${listWithItems.list.listId}")
    }

    UiRow(
        onClick = { openDetail() },
        onLongClick = null,
        content = {
            Row {
                Box(modifier = Modifier) {
                    Text(text = listWithItems.list.name)
                }
                Spacer(Modifier.weight(1f))
                Box {
                    Row {
                        ListTag(itemList = listWithItems.list, short = true)
                    }
                }
            }
        }
    )
}
