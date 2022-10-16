package com.minimalisticapps.packingchecklist.checklist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.UiList
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun ChecklistScreen(checklistId: UUID?) {
    val viewModel = getViewModel<MainViewModel>()

    val checklist = checklistId?.let {
        viewModel.getChecklistWithListsAndItems(it).observeAsState().value
    }

    if (checklistId != null && checklist == null) {
        return
    }

    var checkedItems by remember {
        mutableStateOf(checklist?.checklistHasItems?.associate {
            Pair(
                it.item.itemId, it.isChecked
            )
        } ?: mapOf())
    }

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
                    })
            })
    }
}
