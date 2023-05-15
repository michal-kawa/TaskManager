package com.example.taskmanager.core.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.asEntity
import com.example.taskmanager.core.database.TaskManagerDatabase
import com.example.taskmanager.core.database.dao.TaskDao
import com.example.taskmanager.core.database.model.TaskEntity
import com.example.taskmanager.core.database.model.asModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImplTaskRepository @Inject constructor(private val taskDao: TaskDao) : TaskRepository {
    override fun getTodoTaskList() =
        taskDao.getTodoTaskList().map { it.asModel() }

    override fun getInProgressTaskList() =
        taskDao.getInProgressTaskList().map { it.asModel() }

    override fun getDoneTaskList() =
        taskDao.getDoneTaskList().map { it.asModel() }

    override suspend fun updateTaskStatus(task: Task) =
        taskDao.updateTask(task.asEntity())

    override suspend fun removeTask(task: Task) =
        taskDao.removeTask(task.asEntity())

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.asEntity())
    }
}

interface TaskRepository {
    fun getTodoTaskList(): List<Task>
    fun getInProgressTaskList(): List<Task>
    fun getDoneTaskList(): List<Task>
    suspend fun updateTaskStatus(task: Task)
    suspend fun removeTask(task: Task)
    suspend fun addTask(task: Task)
}