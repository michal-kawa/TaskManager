package com.example.taskmanager.core.common

import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus

interface TaskListViewModel {
    fun refreshTaskList()
    fun removeTask(task: Task)
    fun updateTaskStatus(task: Task, newStatus: TaskStatus)
}