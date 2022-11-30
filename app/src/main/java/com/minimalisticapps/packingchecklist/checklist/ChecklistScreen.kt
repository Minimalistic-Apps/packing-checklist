package com.minimalisticapps.packingchecklist.checklist

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import com.minimalisticapps.packingchecklist.UiRow
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun ChecklistScreen(checklistId: UUID?) {
    val viewModel = getViewModel<MainViewModel>()

    val checklist = checklistId?.let {
        Log.i("AAA", it.toString())
        viewModel.getChecklistWithListsAndItems(it).observeAsState().value
    }

    if (checklistId != null && checklist == null) {
        return
    }

    Log.i("sda", checklist?.checklist?.checklistId.toString())
    Log.i("sda", checklist?.checklistHasItems?.joinToString("\n").toString())

    var checkedItems by remember {
        mutableStateOf(checklist?.checklistHasItems?.associate {
            Pair(
                it.item.itemId, it.isChecked
            )
        } ?: mapOf())
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UiRow(
                content = {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = checklist?.checklist?.name ?: ""
                    )
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart,
            ) {
                UiList(
                    displayItems = checklist?.checklistHasItems ?: listOf(),
                    onDelete = null,
                    onMove = null,
                    content = { checklistHasItem ->
                        val itemId = checklistHasItem.item.itemId
                        val checked = checkedItems[itemId] ?: false

                        ChecklistItemRow(
                            name = checklistHasItem.item.name,
                            checked = checked,
                            onChange = {
                                val newChecked = !checked
                                checkedItems = checkedItems.plus(Pair(itemId, newChecked))
                                checklistHasItem.isChecked = newChecked
                                viewModel.toggleChecklistItem(checklistHasItem)
                            }
                        )
                    }
                )
            }
        }
    }
}
