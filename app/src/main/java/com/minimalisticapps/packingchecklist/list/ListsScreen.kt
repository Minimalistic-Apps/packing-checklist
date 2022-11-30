package com.minimalisticapps.packingchecklist.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel

@Composable
fun ListsScreen(navController: NavHostController) {
    val viewModel = getViewModel<MainViewModel>()
    val itemLists = viewModel.getAllLists().observeAsState()

    UiList(
        displayItems = itemLists.value ?: listOf(),
        onDelete = { viewModel.deleteList(it.list) },
        content = { ListRow(it, navController) },
        onMove = { fromIndex, toIndex ->
            val from = itemLists.value?.get(fromIndex)
            val to = itemLists.value?.get(toIndex)

            if (from != null && to != null) {
                viewModel.reorderLists(from.list, to.list)
            }
        },
    )
}
