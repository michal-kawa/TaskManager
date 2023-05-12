package com.example.taskmanager.core.data.model

import com.example.taskmanager.core.database.model.TaskEntity

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val createDate: String,
    val taskStatus: TaskStatus,
)

enum class TaskStatus {
    TODO, IN_PROGRESS, DONE
}

fun Task.asEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    createDate = createDate,
    taskStatus = taskStatus.name
)