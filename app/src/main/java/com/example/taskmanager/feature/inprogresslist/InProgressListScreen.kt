package com.example.taskmanager.feature.inprogresslist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskmanager.core.common.component.TaskList

@Composable
fun InProgressListScreen(
    navController: NavHostController,
    viewModel: InProgressListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    TaskList(state.listOfTasks, navController, viewModel)
}