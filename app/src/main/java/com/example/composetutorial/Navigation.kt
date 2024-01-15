package com.example.composetutorial

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(
            route = Screen.MainScreen.route
        ) {
            Conversation(navController)
        }
        composable(
            route = Screen.DetailScreen.route
        ){
            DetailScreen(navController)
        }
    }
}