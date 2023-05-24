package com.example.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.taskmanager.feature.addtask.AddTaskScreen
import com.example.taskmanager.feature.donelist.DoneListScreen
import com.example.taskmanager.feature.inprogresslist.InProgressListScreen
import com.example.taskmanager.feature.taskdetail.TaskDetailScreen
import com.example.taskmanager.feature.todolist.TodoListScreen

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Todo.route) {
            TodoListScreen(navController)
        }
        composable(route = Screen.Inprogress.route) {
            InProgressListScreen(navController)
        }
        composable(route = Screen.Done.route) {
            DoneListScreen(navController)
        }
        composable(route = Screen.Add.route) {
            AddTaskScreen()
        }
        composable(
            route = Screen.Task.route,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) {
            TaskDetailScreen()
        }
    }
}