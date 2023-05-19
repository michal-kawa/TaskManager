package com.example.taskmanager.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanager.core.database.model.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE taskStatus LIKE :taskStatus ")
    fun getTaskList(taskStatus: String): List<TaskEntity>

    @Update(entity = TaskEntity::class)
    fun updateTask(vararg task: TaskEntity)

    @Delete
    fun removeTask(vararg task: TaskEntity)

    @Insert
    fun insertTask(vararg task: TaskEntity)
}