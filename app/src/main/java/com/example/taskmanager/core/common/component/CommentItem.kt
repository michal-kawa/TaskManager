package com.example.taskmanager.core.common.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.core.data.model.Comment
import com.example.taskmanager.ui.theme.TaskManagerTheme

@Composable
fun CommentItem(comment: Comment) {
    Surface(
        Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(vertical = 8.dp)) {
            Text(text = comment.message, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = comment.createDate,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun CommentItemNormalMode() {
    TaskManagerTheme {
        CommentItem(comment = getSampleComment())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CommentItemNightMode() {
    TaskManagerTheme {
        CommentItem(comment = getSampleComment())
    }
}

fun getSampleComment() = Comment(1, 1, "To jest przykładowy komentarz", "25.05.2023")