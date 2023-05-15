package com.example.taskmanager.feature.inprogresslist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.core.common.component.TaskList

@Composable
fun InProgressListScreen(viewModel: InProgressListViewModel = hiltViewModel()) {
    val tasks by viewModel.uiState.collectAsState()

    TaskList(tasks.listOfTasks, viewModel)
}