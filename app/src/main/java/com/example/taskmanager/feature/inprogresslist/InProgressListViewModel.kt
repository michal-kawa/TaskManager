package com.example.taskmanager.feature.inprogresslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.example.taskmanager.core.data.repository.TaskRepository
import com.example.taskmanager.feature.todolist.TodoListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InProgressListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(InProgressListUiState())
    val uiState: StateFlow<InProgressListUiState> = _uiState.asStateFlow()

    init {
        refreshTaskList()
    }

    fun updateTaskStatus(taskId: Int, newStatus: TaskStatus) {
        var task = _uiState.value.listOfTasks.find { it.id == taskId }
        if (task != null) {
            viewModelScope.launch(Dispatchers.IO) {
                taskRepository.updateTaskStatus(task.copy(taskStatus = newStatus))
            }
            refreshTaskList()
        }
    }

    private fun refreshTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(listOfTasks = taskRepository.getInProgressTaskList()) }
        }
    }
}