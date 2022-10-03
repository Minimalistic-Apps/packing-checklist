package com.minimalisticapps.packingchecklist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.minimalisticapps.packingchecklist.theme.BackgroundColorForDark
import com.minimalisticapps.packingchecklist.theme.BackgroundColorForLight
import com.minimalisticapps.packingchecklist.theme.PrimaryColorLight
import com.minimalisticapps.packingchecklist.theme.Shapes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : HasKey> UiList(
    displayItems: List<T>,
    onDelete: ((item: T) -> Unit)? = null,
    content: @Composable (item: T) -> Unit,
    onMove: ((fromIndex: Int, toIndex: Int) -> Unit)? = null,
) {
    val isDark = isSystemInDarkTheme()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 0.dp)
    ) {
        items(
            items = displayItems,
            key = { it.key }
        ) {
            if (onDelete !== null) {
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    onDelete(it)
                }

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.5f) },
                    dismissContent = {
                        Card(
                            shape = Shapes.large,
                            elevation = animateDpAsState(
                                if (dismissState.dismissDirection != null) 8.dp else 0.dp
                            ).value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterVertically),
                            backgroundColor = if (isDark) BackgroundColorForDark else BackgroundColorForLight,
                        ) {
                            content(it)
                        }
                    },
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> PrimaryColorLight
                                else -> Color.Red
                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(20f)),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Delete Icon",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    }
                )
            } else {
                Card(
                    shape = Shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = if (isDark) BackgroundColorForDark else BackgroundColorForLight,
                ) {
                    content(it)
                }
            }

            Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f))
        }
    }
}
