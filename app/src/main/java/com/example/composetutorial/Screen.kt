package com.example.composetutorial

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main_screen")
    data object DetailScreen : Screen("detail_screen")
}