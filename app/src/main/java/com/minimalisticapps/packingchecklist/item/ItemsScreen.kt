package com.minimalisticapps.packingchecklist.item

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.minimalisticapps.packingchecklist.MainViewModel
import com.minimalisticapps.packingchecklist.theme.PrimaryColorLight
import com.minimalisticapps.packingchecklist.theme.Shapes

@ExperimentalMaterialApi
@Composable
fun ItemsScreen() {
    val viewModel = getViewModel<MainViewModel>()
    val items = viewModel.getAllItems().observeAsState()

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
                items = items.value ?: listOf(),
                key = { it.item.itemId }
            ) {
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.deleteItem(it.item)
                }

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
                        ) {
                            ItemRow(itemWithLists = it)
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
