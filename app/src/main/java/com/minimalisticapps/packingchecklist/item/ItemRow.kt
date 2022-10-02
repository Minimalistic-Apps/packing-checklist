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
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.Screen
import org.koin.androidx.compose.getViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemRow(itemWithLists: ItemWithLists, navController: NavHostController) {
    val viewModel = getViewModel<MainViewModel>()

    Column(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .clickable { navController.navigate(Screen.EditItem.route + "/${itemWithLists.item.itemId}") }) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row() {
                Text(
                    modifier = Modifier.weight(1.0f, fill = true),
                    text = itemWithLists.item.name
                )
//            ClickToEditText(
//                text = itemWithLists.item.name,
//                isEditable = viewModel.itemIdToEdit.value == itemWithLists.item.itemId,
//                clicked = { viewModel.itemRowClicked(itemWithLists.item.itemId) },
//                onChange = { viewModel.renameItem(itemWithLists.item, it) },
//                onEditDone = { viewModel.itemRowDone(itemWithLists.item.itemId) }
//            )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(8.dp),
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
                            .padding(end = 4.dp),
                        onClick = { /*TODO*/ },
                        colors = ChipDefaults.chipColors(backgroundColor = bgColor)
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                            text = itemList.name
                        )
                    }
                }
            }
        }
    }
}
