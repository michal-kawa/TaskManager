package com.example.taskmanager.feature.addtask

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar

@Composable
fun AddTaskScreen(viewModel: AddTaskViewModel = hiltViewModel()) {

    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf("") }

// Fetching current year, month and day
    var year = calendar[Calendar.YEAR]
    var month = calendar[Calendar.MONTH]
    var day = calendar[Calendar.DAY_OF_MONTH]
    var date = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = taskName,
            onValueChange = { newName -> taskName = newName},
            label = { Text("Name")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = taskDescription,
            onValueChange = { newDescription -> taskDescription = newDescription},
            label = { Text("Description")},
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            minLines = 3,
        )

        val datePickerDialog = DatePickerDialog(
            context,
            {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val corectMonthNumber = if(month + 1 < 10) {
                    '0'+(month+1).toString()
                } else {
                    (month+1).toString()
                }
                selectedDateText = "$dayOfMonth.$corectMonthNumber.$year"
            }, year, month, day
        )

        Text(text = "Selected Date: $selectedDateText", Modifier.clickable { datePickerDialog.show() })

        val onBackPresserDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
        Button(
            onClick = {
                viewModel.addNewTask(
                    Task(0, taskName, taskDescription, selectedDateText, TaskStatus.TODO)
                )
                onBackPresserDispatcher?.onBackPressed()
            }
        ) {
            Text("Add new task")
        }
    }
}

@Preview
@Composable
fun AddTaskPreview() {
    AddTaskScreen()
}