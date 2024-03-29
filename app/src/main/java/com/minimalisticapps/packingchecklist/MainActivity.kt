package com.minimalisticapps.packingchecklist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.minimalisticapps.packingchecklist.checklist.ChecklistScreen
import com.minimalisticapps.packingchecklist.checklist.ChecklistsScreen
import com.minimalisticapps.packingchecklist.checklist.EditChecklistScreen
import com.minimalisticapps.packingchecklist.item.EditItemScreen
import com.minimalisticapps.packingchecklist.item.ItemsScreen
import com.minimalisticapps.packingchecklist.list.EditListScreen
import com.minimalisticapps.packingchecklist.list.ListsScreen
import com.minimalisticapps.packingchecklist.theme.AppTheme
import com.minimalisticapps.packingchecklist.theme.PrimaryColor
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

val RoutesWithAddButton = listOf(
    Screen.Checklists.route,
    Screen.Lists.route,
    Screen.Items.route,
)

val RoutesWithFooter = listOf(
    Screen.Checklists.route,
    Screen.Items.route,
    Screen.Lists.route
)

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            AppTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (RoutesWithFooter.contains(currentRoute)) {
                                BottomNavigationBar(navController = navController)
                            }
                        },
                        floatingActionButton = {
                            if (RoutesWithAddButton.contains(currentRoute)) {
                                ExtendedFloatingActionButton(
                                    backgroundColor = PrimaryColor,
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "image",
                                            tint = Color.White
                                        )
                                    },
                                    text = {
                                        Text(text = "Add", color = Color.White)
                                    },
                                    onClick = {
                                        when (currentRoute) {
                                            Screen.Items.route -> {
                                                navController.navigate(Screen.EditItem.route + "/null")
                                            }
                                            Screen.Lists.route -> {
                                                navController.navigate(Screen.EditList.route + "/null")
                                            }
                                            Screen.Checklists.route -> {
                                                navController.navigate(Screen.EditChecklist.route + "/null")
                                            }
                                        }
                                    }
                                )
                            }
                        },
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .height(60.dp)
                                        .fillMaxWidth()
                                        .background(PrimaryColor)
                                ) {
                                    Toolbar()
                                }
                                Row {
                                    NavHost(
                                        navController = navController,
                                        startDestination = Screen.Checklists.route
                                    )
                                    {
                                        composable(route = Screen.Checklists.route) {
                                            ChecklistsScreen(navController)
                                        }
                                        composable(route = Screen.Items.route) {
                                            ItemsScreen(navController)
                                        }
                                        composable(route = Screen.Lists.route) {
                                            ListsScreen(navController)
                                        }
                                        composable(route = Screen.EditList.route + "/{itemId}") { navBackStack ->
                                            val rawItemId =
                                                navBackStack.arguments?.getString("itemId")
                                            if (rawItemId != null) {
                                                EditListScreen(
                                                    if (rawItemId != "null") UUID.fromString(
                                                        rawItemId
                                                    ) else null,
                                                    navController
                                                )
                                            } else {
                                                Log.e("MainActivity", "rawItemId is null")
                                            }
                                        }
                                        composable(route = Screen.EditItem.route + "/{itemId}") { navBackStack ->
                                            val rawItemId =
                                                navBackStack.arguments?.getString("itemId")
                                            if (rawItemId != null) {
                                                EditItemScreen(
                                                    if (rawItemId != "null") UUID.fromString(
                                                        rawItemId
                                                    ) else null,
                                                    navController
                                                )
                                            } else {
                                                Log.e("MainActivity", "rawItemId is null")
                                            }
                                        }
                                        composable(route = Screen.EditChecklist.route + "/{itemId}") { navBackStack ->
                                            val rawItemId =
                                                navBackStack.arguments?.getString("itemId")
                                            if (rawItemId != null) {
                                                EditChecklistScreen(
                                                    if (rawItemId != "null") UUID.fromString(
                                                        rawItemId
                                                    ) else null,
                                                    navController
                                                )
                                            } else {
                                                Log.e("MainActivity", "rawItemId is null")
                                            }
                                        }
                                        composable(route = Screen.Checklist.route + "/{itemId}") { navBackStack ->
                                            val rawItemId =
                                                navBackStack.arguments?.getString("itemId")
                                            if (rawItemId != null) {
                                                ChecklistScreen(
                                                    if (rawItemId != "null") UUID.fromString(
                                                        rawItemId
                                                    ) else null
                                                )
                                            } else {
                                                Log.e("MainActivity", "rawItemId is null")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
