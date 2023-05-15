package com.example.taskmanager.feature.donelist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.core.common.component.TaskList
import com.example.taskmanager.core.data.model.TaskStatus

@Composable
fun DoneListScreen(viewModel: DoneListViewModel = hiltViewModel()) {
    val tasks by viewModel.uiState.collectAsState()

    TaskList(tasks.listOfTasks, viewModel)
}