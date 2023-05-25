package com.example.taskmanager.feature.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.data.repository.CommentRepository
import com.example.taskmanager.core.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val commentRepository: CommentRepository,
) : ViewModel() {

    private val taskId: Int = checkNotNull(savedStateHandle["taskId"])

    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getTaskById(taskId).collect { task ->
                _uiState.value = _uiState.value.copy(task = task)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            commentRepository.getCommentForTask(taskId).collect { comments ->
                _uiState.value = _uiState.value.copy(comments = comments)
            }
        }
    }
}


//_uiState.update {
//    Timber.d("Ladowanie zadania")
//    it.copy(taskRepository.getTaskById(taskId)
//        .onCompletion {
//            Timber.d("Ladowanie zadania")
//        }
//        .filterNotNull()
//        .first())
//}