package com.example.punchandroidtest.presentation.base_screen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.punchandroidtest.R
import com.example.punchandroidtest.presentation.ui.theme.ColorPrimary
import com.example.punchandroidtest.presentation.ui.theme.LightGray

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        BottomNavItem.FetchApi,
        BottomNavItem.NoteSaved,
        BottomNavItem.Push,
    )
    BottomNavigation(
        contentColor = Color.Black,
        backgroundColor = if(!isSystemInDarkTheme()) Color.White else MaterialTheme.colors.primarySurface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = if(isSystemInDarkTheme()) LightGray else Color.Black,
                unselectedContentColor = if(isSystemInDarkTheme()) Color.Gray else Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}