package com.example.taskmanager.core.data.model

import com.example.taskmanager.core.database.model.CommentEntity

data class Comment(
    val id: Int,
    val taskId: Int,
    val message: String,
    val createDate: String
)

fun Comment.asEntity() = CommentEntity(
    id = id,
    taskId = taskId,
    message = message,
    createDate = createDate,
)