package com.minimalisticapps.packingchecklist.item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.minimalisticapps.packingchecklist.MainViewModel

@Composable
fun ItemsScreen() {

    val viewModel = getViewModel<MainViewModel>()
    val items = viewModel.getAllItems().observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 0.dp)
        ) {
            items(
                items = items.value ?: listOf(),
                key = { it.item.itemId }
            ) {
                ItemRow(itemWithLists = it)
            }
        }
    }
}
