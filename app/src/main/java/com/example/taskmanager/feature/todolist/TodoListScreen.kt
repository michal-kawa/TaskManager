package com.example.taskmanager.feature.todolist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.core.common.component.TaskList
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TodoListScreen(viewModel: TodoListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState() //.observeAsState(listOf()).value

    LaunchedEffect(uiState) {
        viewModel.refreshTaskList()
    }

    TaskList(uiState.listOfTasks, viewModel)
}

@Composable
fun ToDoTaskItem(task: Task) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(task.title)
        Text(task.description)
        Text(task.createDate)
    }
}

fun getNextStatus(task: Task): TaskStatus = when (task.taskStatus) {
    TaskStatus.TODO -> TaskStatus.IN_PROGRESS
    TaskStatus.IN_PROGRESS -> TaskStatus.DONE
    else -> TaskStatus.DONE
}

fun getPreviousStatus(task: Task): TaskStatus = when (task.taskStatus) {
    TaskStatus.IN_PROGRESS -> TaskStatus.TODO
    TaskStatus.DONE -> TaskStatus.IN_PROGRESS
    else -> TaskStatus.TODO
}

//}

