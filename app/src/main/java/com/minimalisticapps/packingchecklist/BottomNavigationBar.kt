package com.minimalisticapps.packingchecklist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.minimalisticapps.packingchecklist.theme.PrimaryColorLight
import com.minimalisticapps.packingchecklist.theme.PrimaryVariantColor

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation(
        backgroundColor = PrimaryVariantColor
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavigationItem(
            selected = currentRoute == Screen.Checklists.route,
            onClick = { navController.navigate(Screen.Checklists.route) },
            icon = {
                Image(
                    painterResource(R.drawable.ic_baseline_checklist_24),
                    "Checklists",
                )
            },
            label = { Text(text = "Checklists") },
            alwaysShowLabel = true,
            modifier = Modifier.background(
                if (currentRoute == Screen.Checklists.route) PrimaryColorLight
                else PrimaryVariantColor
            )
        )

        BottomNavigationItem(
            selected = currentRoute == Screen.Lists.route,
            onClick = { navController.navigate(Screen.Lists.route) },
            icon = {
                Image(
                    painterResource(R.drawable.ic_baseline_format_list_bulleted_24),
                    "List",
                )
            },
            label = { Text(text = "Lists") },
            alwaysShowLabel = true,
            modifier = Modifier.background(
                if (currentRoute == Screen.Lists.route) PrimaryColorLight
                else PrimaryVariantColor
            )
        )

        BottomNavigationItem(
            selected = currentRoute == Screen.Items.route,
            onClick = { navController.navigate(Screen.Items.route) },
            icon = {
                Image(
                    painterResource(R.drawable.ic_baseline_all_inbox_24),
                    "Items",
                )
            },
            label = { Text(text = "Items") },
            alwaysShowLabel = true,
            modifier = Modifier.background(
                if (currentRoute == Screen.Items.route) PrimaryColorLight
                else PrimaryVariantColor
            )
        )
    }
}
