package com.example.taskmanager.core.data.testdao

import com.example.taskmanager.core.database.dao.TaskDao
import com.example.taskmanager.core.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class TestTaskDao : TaskDao {

    private var tasksList = MutableStateFlow(emptyList<TaskEntity>())

    override fun getTaskById(id: Int): Flow<TaskEntity> =
        flow { emit(tasksList.value.filter { it.id == id }.first()) }


    override fun getTaskList(taskStatus: String): Flow<List<TaskEntity>> =
        flow { emit(tasksList.value.filter { it.taskStatus == taskStatus }) }

    override fun updateTask(task: TaskEntity) {
        tasksList.update { tasks ->
            (tasks + task).distinctBy { TaskEntity::id }
        }
    }

    override fun removeTask(task: TaskEntity) {
        tasksList.update { tasks -> tasks.filterNot { it.id == task.id } }
    }

    override fun insertTask(task: TaskEntity) {
        tasksList.update { tasks ->
            (tasks + task).distinctBy { TaskEntity::id }
        }
    }

}