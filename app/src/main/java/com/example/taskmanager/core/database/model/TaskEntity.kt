package com.example.taskmanager.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val createDate: String,
    val taskStatus: String
)

fun TaskEntity.asModel() = Task(
    id = id,
    title = title,
    description = description,
    createDate = createDate,
    taskStatus = when (taskStatus) {
        TaskStatus.TODO.name -> TaskStatus.TODO
        TaskStatus.IN_PROGRESS.name -> TaskStatus.IN_PROGRESS
        TaskStatus.DONE.name -> TaskStatus.DONE
        else -> TaskStatus.TODO
    }
)




