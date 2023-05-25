package com.example.taskmanager.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.taskmanager.core.database.model.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    @Query("SELECT * FROM comment WHERE taskId LIKE :taskId ")
    fun getCommentForTask(taskId: Int): Flow<List<CommentEntity>>

    @Delete
    fun removeComment(comment: CommentEntity)

    @Insert
    fun insertComment(comment: CommentEntity)
}