package com.example.taskmanager.feature.todolist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.core.common.component.TaskList

@Composable
fun TodoListScreen(viewModel: TodoListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        viewModel.refreshTaskList()
    }

    TaskList(uiState.listOfTasks, viewModel)
}
