package com.example.taskmanager.feature.donelist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskmanager.core.common.component.TaskList

@Composable
fun DoneListScreen(
    navController: NavHostController,
    viewModel: DoneListViewModel = hiltViewModel()
) {
    val tasks by viewModel.uiState.collectAsState()

    TaskList(tasks.listOfTasks, navController, viewModel)
}