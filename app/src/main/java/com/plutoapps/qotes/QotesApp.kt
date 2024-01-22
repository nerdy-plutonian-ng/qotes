package com.plutoapps.qotes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plutoapps.qotes.data.models.AppRoutes
import com.plutoapps.qotes.ui.screens.home.HomeScreen
import com.plutoapps.qotes.ui.theme.AppTheme

@Composable
fun QotesApp() {
    val navController = rememberNavController()
    AppTheme {
        NavHost(navController = navController, startDestination = AppRoutes.Home.name){
            composable(route = AppRoutes.Home.name){
                HomeScreen()
            }
        }
    }
}