package com.minimalisticapps.packingchecklist.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.minimalisticapps.packingchecklist.ItemWithLists
import com.minimalisticapps.packingchecklist.Screen
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemRow(itemWithLists: ItemWithLists, navController: NavHostController) {
    val openDetail = {
        navController.navigate(Screen.EditItem.route + "/${itemWithLists.item.itemId}")
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
                    text = itemWithLists.item.name
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            FlowRow() {
                itemWithLists.lists.forEach { itemList ->
                    val rnd = Random(itemList.name.hashCode())
                    val bgColor = Color(
                        red = rnd.nextInt(256),
                        blue = rnd.nextInt(256),
                        green = rnd.nextInt(256),
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
}
