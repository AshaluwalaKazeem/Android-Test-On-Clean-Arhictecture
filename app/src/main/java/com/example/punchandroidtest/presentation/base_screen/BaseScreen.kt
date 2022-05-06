package com.example.punchandroidtest.presentation.base_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.punchandroidtest.presentation.base_screen.components.BottomNav
import com.example.punchandroidtest.presentation.base_screen.components.NavigationGraph

@Composable
fun BaseScreen(
    viewModel: BaseScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomNav(
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            NavigationGraph(navController = navController)
        }
    }
}