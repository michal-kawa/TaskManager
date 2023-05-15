package com.example.taskmanager.feature.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.common.TaskListViewModel
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.example.taskmanager.core.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

//    private var _uiState = MutableStateFlow(AddTaskUiState())
//    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    init {
    }

    fun addNewTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.addTask(task)
        }
    }
}