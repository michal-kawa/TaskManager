package com.example.taskmanager.core.common.component

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.core.data.model.Comment
import com.example.taskmanager.ui.theme.TaskManagerTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentItem(
    comment: Comment,
    isSelected: Boolean,
    onItemLongClick: (Comment) -> Unit
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        Row(modifier =
        if (isSelected)
            Modifier
                .absoluteOffset((-12).dp)
                .combinedClickable(
                    onLongClick = { },
                    onClick = { onItemLongClick(comment) }
                )
        else
            Modifier.combinedClickable(
                onLongClick = { },
                onClick = { onItemLongClick(comment) }
            )
        ) {
            if (isSelected) {
                Checkbox(
                    checked = true,
                    onCheckedChange = { },
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
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
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun CommentItemNormalModeSelected() {
    TaskManagerTheme {
        CommentItem(comment = getSampleComment(), true, {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun CommentItemNormalMode() {
    TaskManagerTheme {
        CommentItem(comment = getSampleComment(), false, {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CommentItemNightModeSelected() {
    TaskManagerTheme {
        CommentItem(comment = getSampleComment(), true, {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CommentItemNightMode() {
    TaskManagerTheme {
        CommentItem(comment = getSampleComment(), false, {})
    }
}

fun getSampleComment() = Comment(1, 1, "To jest przykładowy komentarz", "25.05.2023")