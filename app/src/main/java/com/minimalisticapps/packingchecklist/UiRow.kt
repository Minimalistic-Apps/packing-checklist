package com.minimalisticapps.packingchecklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UiRow(
    content: @Composable () -> Unit,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = if (onClick != null) Modifier.clickable { onClick() } else Modifier
    ) {
        Box(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 18.dp, end = 18.dp)) {
            content()
        }
    }
}
