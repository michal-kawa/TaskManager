package com.example.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.feature.tasklists.DoneListScreen
import com.example.taskmanager.feature.tasklists.InProgressListScreen
import com.example.taskmanager.feature.tasklists.TaskListsViewModel
import com.example.taskmanager.feature.tasklists.TodoListScreen

@Composable
fun SetupNavBottomGraph(
    navController: NavHostController,
    startDestination: String,
    viewModel: TaskListsViewModel
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = ListScreen.Todo.route) {
            TodoListScreen(navController, viewModel)
        }
        composable(route = ListScreen.Inprogress.route) {
            InProgressListScreen(navController, viewModel)
        }
        composable(route = ListScreen.Done.route) {
            DoneListScreen(navController, viewModel)
        }
    }
}

sealed class ListScreen(val route: String) {
    object Todo : MainScreen("todo_screen")
    object Inprogress : MainScreen("inprogress_screen")
    object Done : MainScreen("done_screen")
}
