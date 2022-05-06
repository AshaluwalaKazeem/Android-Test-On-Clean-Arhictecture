package com.example.punchandroidtest.presentation.base_screen.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.punchandroidtest.presentation.fetch_api.FetchApiScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.FetchApi.screen_route) {
        composable(BottomNavItem.FetchApi.screen_route) {
            FetchApiScreen()
        }
        composable(BottomNavItem.NoteSaved.screen_route) {
            FetchApiScreen()
        }
        composable(BottomNavItem.Push.screen_route) {
            FetchApiScreen()
        }
    }
}