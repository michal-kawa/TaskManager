package com.example.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.taskmanager.feature.addtask.AddTaskScreen
import com.example.taskmanager.feature.edittask.EditTaskScreen
import com.example.taskmanager.feature.taskdetail.TaskDetailScreen
import com.example.taskmanager.feature.tasklists.TaskListsScreen

@Composable
fun SetupNavHostGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = MainScreen.TaskList.route) {
            TaskListsScreen(navController)
        }
        composable(route = MainScreen.Add.route) {
            AddTaskScreen(navController)
        }
        composable(
            route = MainScreen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) {
            TaskDetailScreen(navController)
        }
        composable(
            route = MainScreen.EditTask.route,
            arguments = listOf(navArgument("editTaskId") { type = NavType.IntType })
        ) {
            EditTaskScreen(navController)
        }
    }
}

sealed class MainScreen(val route: String) {
    //    object Splash: Screen("splash_screen")
    object TaskList : MainScreen("task_list_screen")
    object Add : MainScreen("add_task_screen")
    object TaskDetail : MainScreen("task_detail_screen/{taskId}") {
        fun createRoute(taskId: String) = "task_detail_screen/$taskId"
    }
    object EditTask : MainScreen("edit_task_screen/{editTaskId}") {
        fun createRoute(taskId: String) = "edit_task_screen/$taskId"
    }
}
