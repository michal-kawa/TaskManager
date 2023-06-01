package com.example.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.feature.tasklists.DoneListScreen
import com.example.taskmanager.feature.tasklists.InProgressListScreen
import com.example.taskmanager.feature.tasklists.TaskListsViewModel
import com.example.taskmanager.feature.tasklists.TodoListScreen

@Composable
fun SetupNavBottomGraph(
    navHostController: NavHostController,
    navBottomBarController: NavHostController,
    startDestination: String,
    viewModel: TaskListsViewModel = hiltViewModel()
) {


    androidx.navigation.compose.NavHost(
        navController = navBottomBarController,
        startDestination = startDestination
    ) {
        composable(route = ListScreen.Todo.route) {
            TodoListScreen(navHostController, viewModel)
        }
        composable(route = ListScreen.Inprogress.route) {
            InProgressListScreen(navHostController, viewModel)
        }
        composable(route = ListScreen.Done.route) {
            DoneListScreen(navHostController, viewModel)
        }
    }
}

sealed class ListScreen(val route: String) {
    object Todo : ListScreen("todo_screen")
    object Inprogress : ListScreen("inprogress_screen")
    object Done : ListScreen("done_screen")
}
