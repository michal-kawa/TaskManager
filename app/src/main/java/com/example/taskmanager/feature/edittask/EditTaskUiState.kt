package com.example.taskmanager.feature.edittask

import com.example.taskmanager.core.data.model.Task

data class EditTaskUiState(
    val taskToChange: Task? = null,
    val isLoading: Boolean = false,
)