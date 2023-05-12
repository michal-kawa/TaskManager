package com.example.taskmanager.core.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskList(
    tasks: List<Task>,
    moveTask: (Int, TaskStatus) -> Unit,
    removeTask: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        this.items(tasks) { task ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        removeTask()
                    }
                    if (it == DismissValue.DismissedToEnd) {
                        var nextStatus = getNextStatus(task)
                        moveTask(task.id, nextStatus)
                    }
                    true
                }
            )
            SwipeToDismiss(
                state = dismissState,
                background = {
                    val color = when (dismissState.dismissDirection) {
                        DismissDirection.StartToEnd -> Color.Green
                        DismissDirection.EndToStart -> Color.Red
                        null -> Color.Transparent
                    }
                    val direction = dismissState.dismissDirection

                    if (direction == DismissDirection.StartToEnd) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "Move to Archive", fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            }

                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.heightIn(5.dp))
                                Text(
                                    text = "Move to Bin",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                )

                            }
                        }
                    }
                },
//                background = {
//                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
//
//                    var nextStatus = getNextStatus(task)
//                    var previousStatus = getPreviousStatus(task)
//
//                    val alignment = when (direction) {
//                        DismissDirection.EndToStart -> Alignment.CenterEnd
//                        DismissDirection.StartToEnd -> Alignment.CenterStart
//                    }
//
//                    val text = when (direction) {
//                        DismissDirection.EndToStart -> if (task.taskStatus == TaskStatus.TODO) {
//                            Text(text = "Remove")
//                        } else {
//                            Text(text = "Move to $previousStatus")
//                        }
//
//                        DismissDirection.StartToEnd -> if (task.taskStatus == TaskStatus.DONE) {
//                            Text(text = "Remove")
//                        } else {
//                            Text(text = "Move to $nextStatus")
//                        }
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color.Cyan)
//                            .padding(12.dp),
//                        contentAlignment = alignment
//                    ) {
//                        text
//                    }
//                }
            ) {
                ToDoTaskItem(task = task)
            }
        }
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