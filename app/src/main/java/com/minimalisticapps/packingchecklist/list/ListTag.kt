package com.minimalisticapps.packingchecklist.list

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minimalisticapps.packingchecklist.ItemList

val char0 = Character.forDigit(0, 10)


private fun getFirstLetter(text: String): String = text
    .replace(Regex("\\p{So}|."), "$0$char0")
    .split(Regex("$char0+"))
    .first()

@Composable
fun ListTag(itemList: ItemList, short: Boolean) {
    val bgColor = calculateListColor(
        "${itemList.name}${itemList.listId}",
        isSystemInDarkTheme()
    )

    Box(
        modifier = (if (short) Modifier.width(24.dp) else Modifier)
            .height(24.dp)
            .padding(end = 4.dp)
            .background(color = bgColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(end = 4.dp, start = 4.dp),
            text = if (short) getFirstLetter(itemList.name) else itemList.name
        )
    }
}
