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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddTaskScreen(viewModel: AddTaskViewModel = hiltViewModel()) {

    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val onBackPresserDispatcher =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    var nameIsEmpty by remember { mutableStateOf(false) }
    var descriptionIsEmpty by remember { mutableStateOf(false) }

    var isDateDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedDate by remember { mutableStateOf("") }
    val startingDate = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))


    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {

            if (!nameIsEmpty && !descriptionIsEmpty) {
                viewModel.addNewTask(
                    Task(0, taskName, taskDescription, selectedDate, TaskStatus.TODO)
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
                label = { Text("Name") },
                onValueChange = { newName ->
                    taskName = newName
                    nameIsEmpty = !newName.matches(Regex("^(?=\\S*\\p{L})\\S+\$"))
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = nameIsEmpty,
                supportingText = { Text(if (nameIsEmpty) "Name can't be empty" else "") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = taskDescription,
                label = { Text("Description") },
                onValueChange = { newDescription ->
                    taskDescription = newDescription
                    descriptionIsEmpty = !newDescription.matches(Regex("^(?=\\S*\\p{L})\\S+\$"))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                isError = descriptionIsEmpty,
                supportingText = { Text(if (descriptionIsEmpty) "Description can't be empty" else "") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = if (selectedDate == "") startingDate else selectedDate,
                label = { Text("Date") },
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { isDateDialogShown = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Date picker icon")
                    }
                }
            )

            if (isDateDialogShown) {
                DatePickerDialog(
                    onDismissRequest = { isDateDialogShown = false },
                    onDateChange = {
                        selectedDate = it.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        isDateDialogShown = false
                    },
                    title = { Text("Select date") },
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