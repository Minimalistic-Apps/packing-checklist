package com.minimalisticapps.packingchecklist.checklist

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun ChecklistScreen(itemId: UUID?, navController: NavHostController) {
    val viewModel = getViewModel<MainViewModel>()

    val checklist =
        itemId?.let { viewModel.getChecklistWithListsAndItems(it).observeAsState().value }

    if (itemId != null && checklist == null) {
        return
    }

    Log.i("xx", checklist.toString())

    UiList(
        displayItems = checklist?.items ?: listOf(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Checkbox(
                    checked = it.isChecked,
                    modifier = Modifier.padding(16.dp),
                    onCheckedChange = { },
                )
                Text(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    text = it.item.name
                )
            }
        }
    )
}
