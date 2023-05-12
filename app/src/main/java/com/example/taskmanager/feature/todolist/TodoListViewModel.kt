package com.example.taskmanager.feature.todolist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class TodoListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        refreshTaskList()
    }

    private var lista = mutableStateListOf(
        Task(0, "Zad1", "Op1", "Dat1", TaskStatus.TODO),
        Task(1, "Zad2", "Op2", "Dat2", TaskStatus.TODO),
        Task(2, "Zad3", "Op3", "Dat3", TaskStatus.TODO),
        Task(3, "Zad4", "Op4", "Dat4", TaskStatus.TODO),
        Task(4, "Zad5", "Op5", "Dat5", TaskStatus.TODO)
    )

    private val _todoListFlow = MutableStateFlow(lista)
    val todoListFlow: StateFlow<List<Task>> get() = _todoListFlow

    fun updateTaskStatus(taskId: Int, newStatus: TaskStatus) {
        var task = _uiState.value.listOfTasks.find { it.id == taskId }
        if (task != null) {
            viewModelScope.launch(Dispatchers.IO) {
                taskRepository.updateTaskStatus(task.copy(taskStatus = newStatus))
            }
            refreshTaskList()
        }
    }

    fun refreshTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(listOfTasks = taskRepository.getTodoTaskList()) }
        }
    }

    fun removeTask(task: Task) {
//        val index = lista.indexOf(task)
//        lista.remove(lista[index])

        viewModelScope.launch(Dispatchers.IO) {
            val taskToDelete = uiState.value.listOfTasks.find { it.id == task.id }!!
            taskRepository.removeTask(taskToDelete)
        }
    }

}