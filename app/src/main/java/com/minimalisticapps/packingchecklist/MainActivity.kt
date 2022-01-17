package com.minimalisticapps.packingchecklist

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import com.minimalisticapps.packingchecklist.state.parseStateFromJson
import com.minimalisticapps.packingchecklist.state.State
import com.minimalisticapps.packingchecklist.ui.theme.PackingChecklistTheme


class MainActivity : ComponentActivity() {
    private val itemsViewModel by viewModels<ItemsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonState = this.getPreferences(Context.MODE_PRIVATE)
            .getString(getString(R.string.state_key), null)

        if (jsonState == null) {
            val state = State()
        } else {
            val state = parseStateFromJson(jsonState)
        }

        setContent {
            PackingChecklistTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ItemsActivityScreen(itemsViewModel)
                }
            }
        }
    }
}

@Composable
private fun ItemsActivityScreen(todoViewModel: ItemsViewModel) {
    ItemsScreen(
        items = todoViewModel.todoItems,
        currentlyEditing = todoViewModel.currentEditItem,
        onAddItem = todoViewModel::addItem,
        onRemoveItem = todoViewModel::removeItem,
        onStartEdit = todoViewModel::onEditItemSelected,
        onEditItemChange = todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone
    )
}
