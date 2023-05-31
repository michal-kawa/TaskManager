package com.example.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.taskmanager.feature.addtask.AddTaskScreen
import com.example.taskmanager.feature.taskdetail.TaskDetailScreen
import com.example.taskmanager.feature.tasklists.TaskListsScreen

@Composable
fun SetupNavHostGraph(navController: NavHostController, startDestination: String) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = MainScreen.TaskList.route) {
            TaskListsScreen(navController)
        }
        composable(route = MainScreen.Add.route) {
            AddTaskScreen()
        }
        composable(
            route = MainScreen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) {
            TaskDetailScreen()
        }
    }
}

sealed class MainScreen(val route: String) {
    //    object Splash: Screen("splash_screen")
    object TaskList : MainScreen("task_list_screen")
    object Add : MainScreen("add_task_screen")
    object TaskDetail : MainScreen("task_screen/{taskId}")
}
