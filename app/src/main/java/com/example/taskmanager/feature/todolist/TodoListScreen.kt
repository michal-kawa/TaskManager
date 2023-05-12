package com.example.taskmanager.feature.todolist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.core.common.component.TaskList
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TodoListScreen(viewModel: TodoListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState() //.observeAsState(listOf()).value
    val lazyListState = rememberLazyListState()

    val todoListState = viewModel.todoListFlow.collectAsState()

//    TaskList(tasks.listOfTasks, viewModel::updateTaskStatus) {}

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {
        items(
            items = uiState.listOfTasks,
            key = { task -> task.id },
            itemContent = { item ->
                val currentItem by rememberUpdatedState(item)
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                            viewModel.removeTask(currentItem)
                            viewModel.refreshTaskList()
                            true
                        } else false
                    }
                )

                if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                    dismissState.isDismissed(DismissDirection.StartToEnd)
                ) {
                    viewModel.removeTask(item)
                    viewModel.refreshTaskList()
                }

                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier
                        .padding(vertical = 1.dp)
                        .animateItemPlacement(),
                    directions = setOf(
                        DismissDirection.StartToEnd,
                        DismissDirection.EndToStart
                    ),
                    dismissThresholds = { direction ->
                        FractionalThreshold(
                            if (direction == DismissDirection.StartToEnd) 0.66f else 0.50f
                        )
                    },
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.LightGray
                                DismissValue.DismissedToEnd -> Color.Green
                                DismissValue.DismissedToStart -> Color.Red
                            }
                        )
                        val alignment = when (direction) {
                            DismissDirection.StartToEnd -> Alignment.CenterStart
                            DismissDirection.EndToStart -> Alignment.CenterEnd
                        }
                        val icon = when (direction) {
                            DismissDirection.StartToEnd -> Icons.Default.Done
                            DismissDirection.EndToStart -> Icons.Default.Delete
                        }
                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            androidx.compose.material.Icon(
                                icon,
                                contentDescription = "Localized description",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {
                        ToDoTaskItem(task = item)
                    }
                )
            }
        )
//        items(
//            items = viewModel.lista,
//            key = { task -> task.id }
//        ) { task ->
//            val currentTask by rememberUpdatedState(newValue = task)
//            val dismissState = rememberDismissState(
//                confirmStateChange = {
//                    viewModel.removeTask(task)
//                    true
//                }
//            )
//            SwipeToDismiss(
//                state = dismissState,
//                modifier = Modifier
//                    .padding(vertical = 1.dp)
//                    .animateItemPlacement(),
//                directions = setOf(
//                    DismissDirection.StartToEnd,
//                    DismissDirection.EndToStart
//                ),
//                dismissThresholds = { direction ->
//                    FractionalThreshold(
//                        if (direction == DismissDirection.StartToEnd) 0.66f else 0.50f
//                    )
//                },
//                background = {
//                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
//                    val color by animateColorAsState(
//                        when (dismissState.targetValue) {
//                            DismissValue.Default -> Color.LightGray
//                            DismissValue.DismissedToEnd -> Color.Green
//                            DismissValue.DismissedToStart -> Color.Red
//                        }
//                    )
//                    val alignment = when (direction) {
//                        DismissDirection.StartToEnd -> Alignment.CenterStart
//                        DismissDirection.EndToStart -> Alignment.CenterEnd
//                    }
//                    val icon = when (direction) {
//                        DismissDirection.StartToEnd -> Icons.Default.Done
//                        DismissDirection.EndToStart -> Icons.Default.Delete
//                    }
//                    val scale by animateFloatAsState(
//                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
//                    )
//
//                    Box(
//                        Modifier
//                            .fillMaxSize()
//                            .background(color)
//                            .padding(horizontal = 20.dp),
//                        contentAlignment = alignment
//                    ) {
//                        androidx.compose.material.Icon(
//                            icon,
//                            contentDescription = "Localized description",
//                            modifier = Modifier.scale(scale)
//                        )
//                    }
//                },
//                dismissContent = {
//                    ToDoTaskItem(task = task)
//                }
//            )
//        }
    }
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

