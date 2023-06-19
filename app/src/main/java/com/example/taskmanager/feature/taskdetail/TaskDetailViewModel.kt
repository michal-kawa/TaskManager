package com.example.taskmanager.feature.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.common.di.IoDispatcher
import com.example.taskmanager.core.data.model.Comment
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.repository.CommentRepository
import com.example.taskmanager.core.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val commentRepository: CommentRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val taskId: Int = checkNotNull(savedStateHandle["taskId"])

    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            taskRepository.getTaskById(taskId).collect { task ->
                _uiState.value = _uiState.value.copy(task = task)
            }
        }

        viewModelScope.launch(ioDispatcher) {
            commentRepository.getCommentForTask(taskId).collect { comments ->
                _uiState.value = _uiState.value.copy(comments = comments)
            }
        }
    }

    fun addCommentAction(commentText: String) {
        val comment = Comment(
            0, _uiState.value.task!!.id, commentText, LocalDate.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            )
        )

        viewModelScope.launch(ioDispatcher) {
            commentRepository.addComment(comment)
            _uiState.value = _uiState.value.copy(comments = _uiState.value.comments + comment)
        }
    }

    fun selectComment(comment: Comment) {
        if (_uiState.value.selectedComments.contains(comment)) {
            _uiState.value =
                _uiState.value.copy(selectedComments = _uiState.value.selectedComments - comment)
        } else {
            _uiState.value =
                _uiState.value.copy(selectedComments = _uiState.value.selectedComments + comment)
        }
    }

    fun removeSelectedComments() {
        val job = viewModelScope.launch(ioDispatcher) {
            uiState.value.selectedComments.forEach {
                commentRepository.removeComment(it)
            }
        }

        viewModelScope.launch {
            job.join()

            _uiState.value =
                _uiState.value.copy(
                    comments = _uiState.value.comments - uiState.value.selectedComments,
                    selectedComments = emptyList()
                )
        }
    }
}