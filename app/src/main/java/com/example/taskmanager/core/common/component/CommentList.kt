package com.example.taskmanager.core.common.component

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.example.taskmanager.feature.taskdetail.TaskDetailUiState
import com.example.taskmanager.ui.theme.TaskManagerTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentList(
    state: TaskDetailUiState,
    onItemLongClick: (Comment) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
        items(
            items = state.comments,
            key = { comment -> comment.id },
            itemContent = { item ->
                val currentItem by rememberUpdatedState(item)
                currentItem.let {

                    CommentItem(
                        comment = it,
                        isSelected = state.selectedComments.contains(it),
                        onItemLongClick = onItemLongClick
                    )

                }
            }
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun CommentListLightMode() {
    TaskManagerTheme {
        CommentList(
            TaskDetailUiState(
                comments = getSampleCommentList(),
                selectedComments = getSampleCommentList()
            ), {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CommentListNightMode() {
    TaskManagerTheme {
        CommentList(
            TaskDetailUiState(
                comments = getSampleCommentList(),
                selectedComments = getSampleCommentList()
            ), {})
    }
}

fun getSampleCommentList() = listOf(
    Comment(1, 0, "To jest pierwszy komentarz dla zadania 1", "20.05.2023"),
    Comment(2, 0, "To jest drugi komentarz dla zadania 1", "23.05.2023"),
    Comment(3, 0, "To jest trzeci komentarz dla zadania 1", "25.05.2023"),
)