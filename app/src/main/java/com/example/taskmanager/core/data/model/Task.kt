package com.example.taskmanager.core.data.model

import com.example.taskmanager.core.database.model.TaskEntity

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val taskStatus: TaskStatus,
)

enum class TaskStatus {
    TODO, IN_PROGRESS, DONE
}


fun Task.asEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    date = date,
    taskStatus = taskStatus.name
)

fun Task.getNextStatus(): TaskStatus? = when (taskStatus) {
    TaskStatus.TODO -> TaskStatus.IN_PROGRESS
    TaskStatus.IN_PROGRESS -> TaskStatus.DONE
    else -> null
}

fun Task.getPreviousStatus(): TaskStatus? = when (taskStatus) {
    TaskStatus.IN_PROGRESS -> TaskStatus.TODO
    TaskStatus.DONE -> TaskStatus.IN_PROGRESS
    else -> null
}