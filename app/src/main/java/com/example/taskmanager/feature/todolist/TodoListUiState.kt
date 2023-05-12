package com.example.taskmanager.feature.todolist

import com.example.taskmanager.core.data.model.Task

data class TodoListUiState(
    val listOfTasks: List<Task> = emptyList()
)