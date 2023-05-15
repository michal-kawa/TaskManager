package com.example.taskmanager.core.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.database.model.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE taskStatus LIKE 'TODO' ")
    fun getTodoTaskList(): List<TaskEntity>

    @Query("SELECT * FROM task WHERE taskStatus LIKE 'IN_PROGRESS' ")
    fun getInProgressTaskList(): List<TaskEntity>

    @Query("SELECT * FROM task WHERE taskStatus LIKE 'DONE' ")
    fun getDoneTaskList(): List<TaskEntity>

    @Update(entity = TaskEntity::class)
    fun updateTask(vararg task: TaskEntity)

    @Delete
    fun removeTask(vararg task: TaskEntity)

    @Insert
    fun insertTask(vararg task: TaskEntity)
}