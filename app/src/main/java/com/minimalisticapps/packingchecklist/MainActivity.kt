package com.minimalisticapps.packingchecklist

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.minimalisticapps.packingchecklist.theme.AppTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.minimalisticapps.packingchecklist.checklist.ChecklistsScreen
import com.minimalisticapps.packingchecklist.item.ItemsScreen
import com.minimalisticapps.packingchecklist.list.ListsScreen
import com.minimalisticapps.packingchecklist.theme.PrimaryColor
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val mContext = LocalContext.current as Activity

            AppTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController = navController) },
                        floatingActionButton = {
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
                                    viewModel.addItem()
                                }
                            )
                        },
                        content = {
                            Column(
                                modifier = Modifier.fillMaxSize()
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
                                            ChecklistsScreen()
                                        }
                                        composable(route = Screen.Items.route) {
                                            ItemsScreen()
                                        }
                                        composable(route = Screen.Lists.route) {
                                            ListsScreen()
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
