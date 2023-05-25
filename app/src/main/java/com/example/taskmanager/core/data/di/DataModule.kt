package com.example.taskmanager.core.data.di

import com.example.taskmanager.core.data.repository.CommentRepository
import com.example.taskmanager.core.data.repository.ImplCommentRepository
import com.example.taskmanager.core.data.repository.ImplTaskRepository
import com.example.taskmanager.core.data.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsTaskRepository(
        taskRepository: ImplTaskRepository
    ): TaskRepository

    @Binds
    fun bindsCommentRepository(
        commentRepository: ImplCommentRepository
    ): CommentRepository
}