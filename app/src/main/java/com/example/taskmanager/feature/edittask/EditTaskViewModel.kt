package com.example.taskmanager.feature.edittask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.common.di.IoDispatcher
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val taskId: Int = checkNotNull(savedStateHandle["editTaskId"])

    private val _uiState = MutableStateFlow(EditTaskUiState())
    val uiState: StateFlow<EditTaskUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { _uiState.value.copy(isLoading = true) }
        viewModelScope.launch(ioDispatcher) {
            taskRepository.getTaskById(taskId).collect { task ->
                _uiState.update {
                    _uiState.value.copy(
                        taskToChange = task,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun saveTask(task: Task) {
        viewModelScope.launch(ioDispatcher) {
            taskRepository.updateTask(task)
        }
        _uiState.update { _uiState.value.copy(taskToChange = task) }
    }

}