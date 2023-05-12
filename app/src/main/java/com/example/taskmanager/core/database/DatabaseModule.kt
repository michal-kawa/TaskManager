package com.example.taskmanager.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideTaskManagerDatabase(
        @ApplicationContext context: Context
    ): TaskManagerDatabase = Room.databaseBuilder(
        context,
        TaskManagerDatabase::class.java,
        "TaskManager-database",
    ).createFromAsset("database/TaskManager-database.db").build()
}