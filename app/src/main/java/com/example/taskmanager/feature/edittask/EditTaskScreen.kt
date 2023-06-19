package com.example.taskmanager.feature.edittask

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.R
import com.example.taskmanager.core.common.component.InputTextField
import com.example.taskmanager.core.common.component.TaskManagerTopBar
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditTaskScreen(
    navHostController: NavHostController,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    EditTaskScreenCompose(navHostController, uiState.value, viewModel::saveTask)

}

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun EditTaskScreenCompose(
    navHostController: NavHostController,
    state: EditTaskUiState,
    saveTask: (Task) -> Unit
) {
    val normalPadding = dimensionResource(id = R.dimen.padding_normal)
    val dateFormat = stringResource(id = R.string.date_format)

    val onBackPresserDispatcher =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val actualTask = state.taskToChange
    var taskName by remember(state.taskToChange) {
        mutableStateOf(
            state.taskToChange?.title ?: ""
        )
    }
    var taskDescription by remember(state.taskToChange) {
        mutableStateOf(
            state.taskToChange?.description ?: ""
        )
    }
    var taskDate by remember(state.taskToChange) {
        mutableStateOf(
            state.taskToChange?.date ?: ""
        )
    }

    var nameIsEmpty by remember { mutableStateOf(false) }
    var descriptionIsEmpty by remember { mutableStateOf(false) }

    var isDateDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TaskManagerTopBar(
                topBarTitle = R.string.editTask_topBar_title,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = stringResource(R.string.back_button_description),
                onNavigationClick = navHostController::popBackStack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (actualTask != null && !nameIsEmpty && !descriptionIsEmpty) {
                    saveTask(
                        Task(
                            actualTask.id,
                            taskName,
                            taskDescription,
                            taskDate,
                            actualTask.taskStatus
                        )
                    )
                    onBackPresserDispatcher?.onBackPressed()
                }
            }) {
                Icon(Icons.Default.Done, "")
            }
        }) {

        if (state.isLoading) {
            Text("Loading")
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
            ) {

                InputTextField(
                    value = taskName,
                    labelResource = R.string.addTask_name_field_label,
                    onValueChange = { newName ->
                        taskName = newName
                        nameIsEmpty = !newName.matches(Regex("^.*[^ ].{0,100}\$"))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nameIsEmpty,
                    supportingText =
                    if (nameIsEmpty) R.string.addTask_title_error_text
                    else R.string.empty_string
                )

                Spacer(modifier = Modifier.height(normalPadding))

                InputTextField(
                    value = taskDescription,
                    labelResource = R.string.addTask_description_field_label,
                    onValueChange = { newDescription ->
                        taskDescription = newDescription
                        descriptionIsEmpty = !newDescription.matches(Regex("^.*[^ ].{0,20}$"))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.addTask_description_field_height)),
                    singleLine = false,
                    isError = descriptionIsEmpty,
                    supportingText =
                    if (descriptionIsEmpty) R.string.addTask_description_field_label
                    else R.string.empty_string
                )

                Spacer(modifier = Modifier.height(normalPadding))

                InputTextField(
                    value = taskDate,
                    labelResource = R.string.addTask_date_field_label,
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth(),
                    trailingIcon = Icons.Default.DateRange,
                    trailingIconOnClick = { isDateDialogShown = true }
                )

                if (isDateDialogShown) {
                    DatePickerDialog(
                        onDismissRequest = { isDateDialogShown = false },
                        onDateChange = { date ->
                            taskDate = date.format(
                                DateTimeFormatter.ofPattern(dateFormat)
                            )
                            isDateDialogShown = false
                        },
                        title = { Text(stringResource(R.string.addTask_datePicker_header_text)) },
                        initialDate = LocalDate.now()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview
@Composable
fun EditTaskPreview() {
    EditTaskScreenCompose(
        rememberNavController(),
        EditTaskUiState(Task(0, "Nazwa taska", "Opis zadania", "7.06.2023", TaskStatus.TODO)),
        { }
    )
}