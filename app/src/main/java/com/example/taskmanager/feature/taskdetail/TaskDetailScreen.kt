package com.example.taskmanager.feature.taskdetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.example.taskmanager.ui.theme.TaskManagerTheme

@Composable
fun TaskDetailScreen(viewModel: TaskDetailViewModel = hiltViewModel()) {

    val state = viewModel.uiState.collectAsState()

    TaskDetailScreenComposable(state.value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskDetailScreenComposable(state: TaskDetailUiState) {
    Surface {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
            state.task?.let { task ->

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = task.title,
                    onValueChange = {},
                    label = { Text("Title", Modifier.padding(horizontal = 4.dp),
                        style = MaterialTheme.typography.bodySmall) },
                    readOnly = true
                )
                Spacer(Modifier.height(24.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = task.description,
                    onValueChange = {},
                    label = { Text("Description",
                        style = MaterialTheme.typography.bodySmall) },
                    readOnly = true
                )
                Spacer(Modifier.height(24.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = task.date,
                    onValueChange = {},
                    label = { Text("Date",
                        style = MaterialTheme.typography.bodySmall) },
                    readOnly = true
                )

                Spacer(Modifier.height(16.dp))

                Text("Comments")
                Divider(thickness = 2.dp)



            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL, showSystemUi = true)
fun TaskDetailScreenLightMode() {
    TaskManagerTheme {
        TaskDetailScreenComposable(getSampleTaskDetailScreen())
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
fun TaskDetailScreenNightMode() {
    TaskManagerTheme {
        TaskDetailScreenComposable(getSampleTaskDetailScreen())
    }
}

fun getSampleTaskDetailScreen() = TaskDetailUiState(
    Task(
        0,
        "Znak zakazu parkowania przy ul. Litomskiej",
        "To jest bardzo długi opis zadania, ponieważ potrzebuje czegoś takiego do testów. Dlatego pisze taki długi opis, który będzie się zawijał na kilka linijek.",
        "23.05.2023",
        TaskStatus.TODO
    )
)