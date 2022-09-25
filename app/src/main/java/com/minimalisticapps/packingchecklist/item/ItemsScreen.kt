package com.minimalisticapps.packingchecklist.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel

@Composable
fun ItemsScreen(navController: NavHostController) {
    val viewModel = getViewModel<MainViewModel>()
    val items = viewModel.getAllItems().observeAsState()

    UiList(
        displayItems = items.value ?: listOf(),
        // Todo: SwipeToDismiss interferes with scroll
//        onDelete = { viewModel.deleteItem(it.item) },
        content = { ItemRow(it, navController) },
        onMove = { fromIndex, toIndex ->
            val from = items.value?.get(fromIndex)
            val to = items.value?.get(toIndex)

            if (from != null && to != null) {
                viewModel.reorderItems(from.item, to.item)
            }
        },
    )
}
