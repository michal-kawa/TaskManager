package com.example.taskmanager.feature.donelist

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
class DoneListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel(), TaskListViewModel {

    private var _uiState = MutableStateFlow(DoneListUiState())
    val uiState: StateFlow<DoneListUiState> = _uiState.asStateFlow()

    init {
        refreshTaskList()
    }

    override fun refreshTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getDoneTaskList().collect { taskList ->
                _uiState.update { it.copy(listOfTasks = taskList) }
            }
        }
    }

    override fun removeTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskToDelete = uiState.value.listOfTasks.find { it.id == task.id }!!
            taskRepository.removeTask(taskToDelete)
        }
    }

    override fun updateTaskStatus(task: Task, newStatus: TaskStatus) {
        val taskToUpdate = _uiState.value.listOfTasks.find { it.id == task.id }
        if (taskToUpdate != null) {
            viewModelScope.launch(Dispatchers.IO) {
                taskRepository.updateTask(task.copy(taskStatus = newStatus))
            }
            refreshTaskList()
        }
    }
}