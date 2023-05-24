package com.example.taskmanager.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanager.core.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE id LIKE :id ")
    fun getTaskById(id: Int): Flow<TaskEntity>

    @Query("SELECT * FROM task WHERE taskStatus LIKE :taskStatus ")
    fun getTaskList(taskStatus: String): Flow<List<TaskEntity>>

    @Update(entity = TaskEntity::class)
    fun updateTask(task: TaskEntity)

    @Delete
    fun removeTask(task: TaskEntity)

    @Insert
    fun insertTask(task: TaskEntity)
}