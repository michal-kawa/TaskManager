package com.example.taskmanager.core.database

import com.example.taskmanager.core.database.dao.CommentDao
import com.example.taskmanager.core.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModules {
    @Provides
    fun providesTaskDao(
        database: TaskManagerDatabase
    ): TaskDao = database.taskDao()

    @Provides
    fun providesCommentDao(
        database: TaskManagerDatabase
    ): CommentDao = database.commentDao()
}