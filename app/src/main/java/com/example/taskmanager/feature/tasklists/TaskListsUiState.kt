package com.example.taskmanager.feature.tasklists

import com.example.taskmanager.core.data.model.Task

data class TaskListsUiState(
    val taskList: List<Task> = emptyList(),
    val todoTaskList: List<Task> = emptyList(),
    val inprogressTaskList: List<Task> = emptyList(),
    val doneTaskList: List<Task> = emptyList()
)