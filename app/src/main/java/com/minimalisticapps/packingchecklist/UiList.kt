package com.minimalisticapps.packingchecklist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
    onDelete: (item: T) -> Unit,
    content: @Composable (item: T) -> Unit
) {
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
                items = displayItems,
                key = { it.key }
            ) {
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                   onDelete(it)
                }

                val isDark = isSystemInDarkTheme()

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {
                        Card(
                            shape = Shapes.large,
                            elevation = animateDpAsState(
                                if (dismissState.dismissDirection != null) 8.dp else 0.dp
                            ).value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .align(alignment = Alignment.CenterVertically)
                                .drawBehind {
                                    drawLine(
                                        color = if (isDark) Color.Gray else Color.LightGray,
                                        Offset(0f, 0f),
                                        Offset(size.width, 0f),
                                        strokeWidth = Stroke.DefaultMiter
                                    )
                                },
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
            }
        }
    }
}
