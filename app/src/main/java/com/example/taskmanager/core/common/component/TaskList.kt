package com.example.taskmanager.core.common.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskmanager.R
import com.example.taskmanager.core.common.TaskListViewModel
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.example.taskmanager.core.data.model.getNextStatus
import com.example.taskmanager.core.data.model.getPreviousStatus

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TaskList(
    tasks: List<Task>,
    navController: NavHostController,
    viewModel: TaskListViewModel
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {
        items(
            items = tasks,
            key = { task -> task.id },
            itemContent = { item ->
                val currentItem by rememberUpdatedState(item)
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        when (it) {
                            DismissValue.DismissedToStart -> {
                                swipeEndToStartFunctionality(currentItem, viewModel)
                                true
                            }

                            DismissValue.DismissedToEnd -> {
                                swipeStartToEndFunctionality(currentItem, viewModel)
                                true
                            }

                            else -> false
                        }
                    }
                )

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    swipeEndToStartFunctionality(currentItem, viewModel)
                }

                if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                    swipeStartToEndFunctionality(currentItem, viewModel)
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
                    dismissThresholds = {
                        FractionalThreshold(0.2f)
                    },
                    background = {
                        SwipeBackground(item, dismissState)
                    },
                    dismissContent = {
                        TaskItem(task = item, onClick = navController::navigate)
                    }
                )
            }
        )
    }
}

fun swipeEndToStartFunctionality(task: Task, viewModel: TaskListViewModel) {
    when (task.taskStatus) {
        TaskStatus.TODO -> viewModel.removeTask(task)
        TaskStatus.IN_PROGRESS -> viewModel.updateTaskStatus(task, TaskStatus.TODO)
        TaskStatus.DONE -> viewModel.updateTaskStatus(task, TaskStatus.IN_PROGRESS)
    }
    viewModel.refreshTaskList()
}

fun swipeStartToEndFunctionality(task: Task, viewModel: TaskListViewModel) {
    when (task.taskStatus) {
        TaskStatus.TODO -> viewModel.updateTaskStatus(task, TaskStatus.IN_PROGRESS)
        TaskStatus.IN_PROGRESS -> viewModel.updateTaskStatus(task, TaskStatus.DONE)
        TaskStatus.DONE -> viewModel.removeTask(task)
    }
    viewModel.refreshTaskList()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeBackground(task: Task, dismissState: DismissState) {

    val nextStatus = task.getNextStatus()
    val previousStatus = task.getPreviousStatus()

    val mapOfStatus = mapOf(
        TaskStatus.TODO to ImageVector.vectorResource(id = R.drawable.todo_icon),
        TaskStatus.IN_PROGRESS to ImageVector.vectorResource(R.drawable.inprogress_icon),
        TaskStatus.DONE to ImageVector.vectorResource(R.drawable.done_icon),
    )

    val direction = dismissState.dismissDirection ?: return
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.LightGray
            DismissValue.DismissedToEnd -> if (nextStatus != null) Color.Green else Color.Red
            DismissValue.DismissedToStart -> if (previousStatus != null) Color.Green else Color.Red
        }, label = ""
    )
    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }
    val icon = when (direction) {
        DismissDirection.StartToEnd -> mapOfStatus[nextStatus] ?: Icons.Default.Delete
        DismissDirection.EndToStart -> mapOfStatus[previousStatus] ?: Icons.Default.Delete
    }
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0f else 1f,
        label = ""
    )

    val actionTextResource = when (direction) {
        DismissDirection.StartToEnd ->
            if (nextStatus != null) R.string.tasklist_action_move_to
            else R.string.tasklist_action_remove

        DismissDirection.EndToStart ->
            if (previousStatus != null) R.string.tasklist_action_move_to
            else R.string.tasklist_action_remove
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Row {
            Text(
                stringResource(actionTextResource),
                modifier = Modifier.scale(scale)
            )
            Icon(
                icon,
                contentDescription = stringResource(R.string.tasklist_action_icon_description),
                modifier = Modifier.scale(scale)
            )
        }
    }
}