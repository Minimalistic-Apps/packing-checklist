package com.minimalisticapps.packingchecklist.checklist

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.minimalisticapps.packingchecklist.UiRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChecklistItemRow(name: String, checked: Boolean, onChange: () -> Unit) {
    val mContext = LocalContext.current as Activity

    UiRow(content = {
        Row {
            Checkbox(
                checked = checked,
                modifier = Modifier
                    .combinedClickable(
                        onClick = {
                            Toast
                                .makeText(
                                    mContext,
                                    "Long press to ${if (checked) "uncheck" else "check"}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        },
                        onLongClick = { onChange() })
                    .align(alignment = Alignment.CenterVertically)
                    .padding(end = 8.dp),
                onCheckedChange = null,
            )
            Text(
                text = name,
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
            )
        }
    })
}
