package com.example.taskmanager.core.common.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.example.taskmanager.ui.theme.TaskManagerTheme

@Composable
fun TaskItem(task: Task, onClick: (String) -> Unit) {
    val normalPadding = 16.dp
    Surface {
        Column(modifier = Modifier.clickable { onClick("task_detail_screen/" + task.id) },) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(normalPadding),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(4.dp, 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = task.date,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        }
    }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TaskItemPreview() {
    TaskManagerTheme(dynamicColor = false) {
        TaskItem(
            task = Task(
                0,
                "Zadanie 1",
                "To jest bardzo długi opis zadania, ponieważ potrzebuje czegoś takiego do testów. Dlatego pisze taki długi opis, który będzie się zawijał na kilka linijek.",
                "23.05.2023",
                TaskStatus.TODO
            ),
            onClick = {  }
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TaskItemPreviewNight() {
    TaskManagerTheme(dynamicColor = false) {
        TaskItem(
            task = Task(
                0,
                "Zadanie 1",
                "To jest bardzo długi opis zadania, ponieważ potrzebuje czegoś takiego do testów. Dlatego pisze taki długi opis, który będzie się zawijał na kilka linijek.",
                "23.05.2023",
                TaskStatus.TODO
            ),
            onClick = {  }
        )
    }
}