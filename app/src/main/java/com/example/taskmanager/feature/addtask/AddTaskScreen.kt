package com.example.taskmanager.feature.addtask

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.taskmanager.R
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddTaskScreen(viewModel: AddTaskViewModel = hiltViewModel()) {

    val normalPadding = dimensionResource(id = R.dimen.padding_normal)
    val dateFormat = stringResource(id = R.string.date_format)

    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    val onBackPresserDispatcher =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    var nameIsEmpty by remember { mutableStateOf(false) }
    var descriptionIsEmpty by remember { mutableStateOf(false) }

    var isDateDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedDate by remember { mutableStateOf("") }
    var startingDate = LocalDate.now()
        .format(DateTimeFormatter.ofPattern(dateFormat))



    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            if (!nameIsEmpty && !descriptionIsEmpty) {
                viewModel.addNewTask(
                    Task(0, taskName, taskDescription, startingDate, TaskStatus.TODO)
                )
                onBackPresserDispatcher?.onBackPressed()
            }
        }) {
            Icon(Icons.Default.Add, "")
        }
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = taskName,
                label = { Text(stringResource(R.string.addTask_name_field_label)) },
                onValueChange = { newName ->
                    taskName = newName
                    nameIsEmpty = !newName.matches(Regex("^.*[^ ].{0,100}\$"))
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = nameIsEmpty,
                supportingText = {
                    Text(
                        if (nameIsEmpty) stringResource(
                            R.string.addTask_input_error_text,
                            stringResource(R.string.addTask_name_field_label)
                        ) else ""
                    )
                }
            )

            Spacer(modifier = Modifier.height(normalPadding))

            OutlinedTextField(
                value = taskDescription,
                label = { Text(stringResource(R.string.addTask_description_field_label)) },
                onValueChange = { newDescription ->
                    taskDescription = newDescription
                    descriptionIsEmpty = !newDescription.matches(Regex("^.*[^ ].{0,20}$"))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.addTask_description_field_height)),
                isError = descriptionIsEmpty,
                supportingText = {
                    Text(
                        if (descriptionIsEmpty) stringResource(
                            R.string.addTask_input_error_text,
                            stringResource(R.string.addTask_description_field_label)
                        ) else ""
                    )
                }
            )

            Spacer(modifier = Modifier.height(normalPadding))

            OutlinedTextField(
                value = if (selectedDate == "") startingDate else selectedDate,
                label = { Text("Date") },
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { isDateDialogShown = true }) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = stringResource(id = R.string.addTask_datePicker_icon_description)
                        )
                    }
                }
            )

            if (isDateDialogShown) {
                DatePickerDialog(
                    onDismissRequest = { isDateDialogShown = false },
                    onDateChange = { date ->
                        startingDate = date.format(
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

@Preview
@Composable
fun AddTaskPreview() {
    AddTaskScreen()
}