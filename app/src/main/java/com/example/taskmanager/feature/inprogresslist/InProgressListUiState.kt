package com.example.taskmanager.feature.inprogresslist

import com.example.taskmanager.core.data.model.Task

data class InProgressListUiState(
    val listOfTasks: List<Task> = emptyList()
)