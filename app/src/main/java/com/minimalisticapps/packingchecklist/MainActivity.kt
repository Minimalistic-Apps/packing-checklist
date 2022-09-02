package com.minimalisticapps.packingchecklist

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.minimalisticapps.packingchecklist.theme.AppTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.minimalisticapps.packingchecklist.theme.PrimaryColor

class MainActivity : ComponentActivity() {
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
                                            ChecklistDetailScreen()
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
