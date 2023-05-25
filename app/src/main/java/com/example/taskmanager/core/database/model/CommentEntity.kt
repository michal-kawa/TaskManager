package com.example.taskmanager.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.core.data.model.Comment

@Entity(tableName = "comment")
data class CommentEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val taskId: Int,
    val message: String,
    val createDate: String
)

fun CommentEntity.asModel() = Comment(
    id = id,
    taskId = taskId,
    message = message,
    createDate = createDate,
)




