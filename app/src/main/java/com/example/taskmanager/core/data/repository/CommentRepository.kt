package com.example.taskmanager.core.data.repository

import com.example.taskmanager.core.data.model.Comment
import com.example.taskmanager.core.data.model.asEntity
import com.example.taskmanager.core.database.dao.CommentDao
import com.example.taskmanager.core.database.model.asModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImplCommentRepository @Inject constructor(private val commentDao: CommentDao) :
    CommentRepository {
    override fun getCommentForTask(taskId: Int) =
        commentDao.getCommentForTask(taskId).map { list -> list.map { it.asModel() } }

    override suspend fun removeComment(comment: Comment) =
        commentDao.removeComment(comment.asEntity())

    override suspend fun addComment(comment: Comment) {
        commentDao.insertComment(comment.asEntity())
    }
}

interface CommentRepository {
    fun getCommentForTask(taskId: Int): Flow<List<Comment>>
    suspend fun removeComment(comment: Comment)
    suspend fun addComment(comment: Comment)
}