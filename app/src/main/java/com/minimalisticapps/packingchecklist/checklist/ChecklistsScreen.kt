package com.minimalisticapps.packingchecklist.checklist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.minimalisticapps.packingchecklist.ChecklistWithListsAndItems
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel

@Composable
fun ChecklistsScreen(navController: NavHostController) {
    val viewModel = getViewModel<MainViewModel>()
    val checklists = viewModel.getAllChecklists().observeAsState()

    UiList(
        displayItems = checklists.value ?: listOf<ChecklistWithListsAndItems>(),
        content = { ChecklistRow(it, navController) },
    )
}
