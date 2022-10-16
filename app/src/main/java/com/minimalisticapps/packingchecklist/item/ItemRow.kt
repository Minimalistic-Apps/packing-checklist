package com.minimalisticapps.packingchecklist.item

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.minimalisticapps.packingchecklist.ItemWithLists
import com.minimalisticapps.packingchecklist.Screen
import com.minimalisticapps.packingchecklist.UiRow
import com.minimalisticapps.packingchecklist.list.calculateListColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemRow(itemWithLists: ItemWithLists, navController: NavHostController) {
    val openDetail = {
        navController.navigate(Screen.EditItem.route + "/${itemWithLists.item.itemId}")
    }

    UiRow(
        onClick = { openDetail() },
        onLongClick = null,
        content = {
            Column {
                Text(text = itemWithLists.item.name)

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                ) {
                    itemWithLists.lists.forEach { itemList ->
                        val bgColor = calculateListColor(
                            "${itemList.name}${itemList.listId}",
                            isSystemInDarkTheme()
                        )

                        Chip(
                            modifier = Modifier
                                .height(24.dp)
                                .padding(end = 1.dp, bottom = 1.dp),
                            onClick = { openDetail() },
                            colors = ChipDefaults.chipColors(backgroundColor = bgColor)
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 0.dp, end = 0.dp),
                                text = itemList.name
                            )
                        }
                    }
                }
            }
        }
    )
}
