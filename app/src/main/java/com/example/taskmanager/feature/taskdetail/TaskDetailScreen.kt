package com.example.taskmanager.feature.taskdetail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.R
import com.example.taskmanager.core.common.component.CommentList
import com.example.taskmanager.core.common.component.TaskManagerTopBar
import com.example.taskmanager.core.data.model.Comment
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.example.taskmanager.navigation.MainScreen
import com.example.taskmanager.ui.theme.TaskManagerTheme
import com.example.taskmanager.utils.ActionTopBarOption

@Composable
fun TaskDetailScreen(
    navHostController: NavHostController,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    TaskDetailScreenComposable(
        navHostController,
        state.value,
        viewModel::addCommentAction,
        viewModel::selectComment,
        viewModel::removeSelectedComments
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskDetailScreenComposable(
    navHostController: NavHostController,
    state: TaskDetailUiState,
    addCommentAction: (String) -> Unit,
    selectCommentAction: (Comment) -> Unit,
    onDeleteCommentActionClick: () -> Unit
) {

    var isNewCommentDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TaskManagerTopBar(
                topBarTitle = R.string.taskDetail_topBar_title,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = stringResource(com.example.taskmanager.R.string.back_button_description),
                onNavigationClick = navHostController::popBackStack,
                actionOptions = listOf(
                    ActionTopBarOption(
                        state.deleteCommentOption,
                        Icons.Default.Delete,
                        stringResource(com.example.taskmanager.R.string.delete_comments_action_icon_description),
                        onDeleteCommentActionClick
                    ),
                    ActionTopBarOption(
                        !state.isEditTaskModeEnabled,
                        Icons.Default.Edit,
                        stringResource(com.example.taskmanager.R.string.edit_task_action_icon_description)
                    ) { navHostController.navigate(MainScreen.EditTask.createRoute("${state.task?.id}")) }
                )
            )
        }
    ) { paddingValues ->
        Surface {
            Column(
                Modifier
                    .padding(16.dp)
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                state.task?.let { task ->

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        value = task.title,
                        onValueChange = { },
                        label = {
                            Text(
                                "Title", Modifier.padding(horizontal = 4.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        readOnly = true
                    )
                    Spacer(Modifier.height(24.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        value = task.description,
                        onValueChange = { },
                        label = {
                            Text(
                                "Description",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        readOnly = true
                    )
                    Spacer(Modifier.height(24.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        value = task.date,
                        onValueChange = {},
                        label = {
                            Text(
                                "Date",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        readOnly = true
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Comments", style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        IconButton(
                            modifier = Modifier.size(36.dp),
                            onClick = { isNewCommentDialogShown = true },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Divider(Modifier.height(2.dp))

                    if (state.comments.isEmpty()) {
                        Text(
                            "Empty list of comments",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        CommentList(state, selectCommentAction)
                    }

                    if (isNewCommentDialogShown) {
                        AddCommentDialog({ isNewCommentDialogShown = false }, addCommentAction)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommentDialog(onDismissRequest: () -> Unit, onConfirmRequest: (String) -> Unit) {
    var comment: String by rememberSaveable("") {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(),
                onClick = {
                    onConfirmRequest(comment)
                    onDismissRequest()
                },
                enabled = comment.matches(Regex("^.*[^ ].{0,100}\$")),
            ) {
                Text(stringResource(id = R.string.ok_button_label))
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(),
                onClick = { onDismissRequest() },
            ) {
                Text(stringResource(id = R.string.cancel_button_label))
            }
        },
        title = { Text("Add new comment") },
        icon = null,
        text = {
            OutlinedTextField(
                value = comment,
                onValueChange = { newComment -> comment = newComment },
                label = { Text("New comment") },
                modifier = Modifier
                    .height(150.dp)
                    .widthIn(max = 280.dp)
                    .focusRequester(focusRequester)
            )
        },
//        shape = shape,
        containerColor = AlertDialogDefaults.containerColor,
        titleContentColor = AlertDialogDefaults.titleContentColor,
        tonalElevation = AlertDialogDefaults.TonalElevation,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    )
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL, showSystemUi = true)
fun TaskDetailScreenLightMode() {
    TaskManagerTheme {
        TaskDetailScreenComposable(
            rememberNavController(),
            getSampleTaskDetailScreen(),
            {},
            {},
            {})
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
fun TaskDetailScreenNightMode() {
    TaskManagerTheme {
        TaskDetailScreenComposable(
            rememberNavController(),
            getSampleTaskDetailScreen(),
            {},
            {},
            {})
    }
}

fun getSampleTaskDetailScreen() = TaskDetailUiState(
    Task(
        0,
        "Znak zakazu parkowania przy ul. Litomskiej",
        "To jest bardzo długi opis zadania, ponieważ potrzebuje czegoś takiego do testów. Dlatego pisze taki długi opis, który będzie się zawijał na kilka linijek.",
        "23.05.2023",
        TaskStatus.TODO
    ),
    listOf(
        Comment(1, 0, "To jest pierwszy komentarz dla zadania 1", "20.05.2023"),
        Comment(2, 0, "To jest drugi komentarz dla zadania 1", "23.05.2023"),
        Comment(3, 0, "To jest trzeci komentarz dla zadania 1", "25.05.2023"),
    )
)