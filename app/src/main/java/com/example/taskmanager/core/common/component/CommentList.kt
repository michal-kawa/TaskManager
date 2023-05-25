package com.example.taskmanager.core.common.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskmanager.core.data.model.Comment
import com.example.taskmanager.ui.theme.TaskManagerTheme

@Composable
fun CommentList(
    comments: List<Comment>,
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
        items(
            items = comments,
            key = { comment -> comment.id },
            itemContent = { item ->
                val currentItem by rememberUpdatedState(item)
                CommentItem(comment = currentItem)
            }
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun CommentListLightMode() {
    TaskManagerTheme {
        CommentList(comments = getSampleCommentList())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CommentListNightMode() {
    TaskManagerTheme {
        CommentList(comments = getSampleCommentList())
    }
}

fun getSampleCommentList() = listOf(
    Comment(1, 0, "To jest pierwszy komentarz dla zadania 1", "20.05.2023"),
    Comment(2, 0, "To jest drugi komentarz dla zadania 1", "23.05.2023"),
    Comment(3, 0, "To jest trzeci komentarz dla zadania 1", "25.05.2023"),
)