package com.example.taskmanager.core.data.repository

import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.example.taskmanager.core.data.model.asEntity
import com.example.taskmanager.core.database.dao.TaskDao
import com.example.taskmanager.core.database.model.asModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImplTaskRepository @Inject constructor(private val taskDao: TaskDao) : TaskRepository {
    override fun getTaskById(id: Int) =
        taskDao.getTaskById(id).map { it.asModel() }

    override fun getTodoTaskList() =
        taskDao.getTaskList(TaskStatus.TODO.name).map { it.map { it.asModel() } }

    override fun getInProgressTaskList() =
        taskDao.getTaskList(TaskStatus.IN_PROGRESS.name).map { it.map { it.asModel() } }

    override fun getDoneTaskList() =
        taskDao.getTaskList(TaskStatus.DONE.name).map { it.map { it.asModel() } }

    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(task.asEntity())

    override suspend fun removeTask(task: Task) =
        taskDao.removeTask(task.asEntity())

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.asEntity())
    }
}

interface TaskRepository {
    fun getTaskById(id: Int): Flow<Task>
    fun getTodoTaskList(): Flow<List<Task>>
    fun getInProgressTaskList(): Flow<List<Task>>
    fun getDoneTaskList(): Flow<List<Task>>
    suspend fun updateTask(task: Task)
    suspend fun removeTask(task: Task)
    suspend fun addTask(task: Task)
}