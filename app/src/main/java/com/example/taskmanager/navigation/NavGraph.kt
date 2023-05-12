package com.example.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.feature.addtask.AddTaskScreen
import com.example.taskmanager.feature.donelist.DoneListScreen
import com.example.taskmanager.feature.inprogresslist.InProgressListScreen
import com.example.taskmanager.feature.todolist.TodoListScreen

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
//        composable(route = Screen.Splash.route) {
//            SplashScreen(navController = navController)
//        }
        composable(route = Screen.Todo.route) {
            TodoListScreen()
        }
        composable(route = Screen.Inprogress.route) {
            InProgressListScreen()
        }
        composable(route = Screen.Done.route) {
            DoneListScreen()
        }
        composable(route = Screen.Add.route) {
            AddTaskScreen()
        }
    }
}