package com.example.taskmanager.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskmanager.core.database.dao.CommentDao
import com.example.taskmanager.core.database.dao.TaskDao
import com.example.taskmanager.core.database.model.CommentEntity
import com.example.taskmanager.core.database.model.TaskEntity

@Database(entities = [TaskEntity::class, CommentEntity::class], version = 1, exportSchema = false)
abstract class TaskManagerDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun commentDao(): CommentDao
}