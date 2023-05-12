package com.example.taskmanager.navigation

sealed class Screen(val route: String) {
    //    object Splash: Screen("splash_screen")
    object Lists : Screen("lists_screen")
    object Todo : Screen("todo_screen")
    object Inprogress : Screen("inprogress_screen")
    object Done : Screen("done_screen")
    object Add : Screen("add_screen")
}


