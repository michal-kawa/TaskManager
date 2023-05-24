package com.example.taskmanager.feature.todolist

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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel(), TaskListViewModel {

    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        refreshTaskList()
        Timber.d("Lista zadan todo")
    }

    override fun refreshTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getTodoTaskList().collect { taskList ->
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
        var taskToUpdate = _uiState.value.listOfTasks.find { it.id == task.id }
        if (taskToUpdate != null) {
            viewModelScope.launch(Dispatchers.IO) {
                taskRepository.updateTask(task.copy(taskStatus = newStatus))
            }
            refreshTaskList()
        }
    }


}