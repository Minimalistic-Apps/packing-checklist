package com.minimalisticapps.packingchecklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UiRow(
    content: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null
) {
    Column(
        modifier = if (onClick != null) Modifier.combinedClickable(
            onClick = { onClick() },
            onLongClick = if (onLongClick != null) ({ onLongClick() }) else null,
        ) else Modifier
    ) {
        Box(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 18.dp, end = 18.dp)) {
            content()
        }
    }
}
