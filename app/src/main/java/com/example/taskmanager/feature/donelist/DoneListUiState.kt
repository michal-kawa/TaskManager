package com.example.taskmanager.feature.donelist

import com.example.taskmanager.core.data.model.Task

data class DoneListUiState(
    val listOfTasks: List<Task> = emptyList()
)