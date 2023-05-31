package com.example.taskmanager.feature.taskdetail

import com.example.taskmanager.core.data.model.Comment
import com.example.taskmanager.core.data.model.Task

data class TaskDetailUiState(
    var task: Task? = null,
    var comments: List<Comment> = emptyList(),
    var selectedComments: List<Comment> = emptyList(),
    var showCommentsCheckBox: Boolean = false
)
