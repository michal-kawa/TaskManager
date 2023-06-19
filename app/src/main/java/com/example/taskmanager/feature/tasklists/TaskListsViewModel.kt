package com.example.taskmanager.feature.tasklists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.common.di.IoDispatcher
import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
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
class TaskListsViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListsUiState())
    val uiState: StateFlow<TaskListsUiState> = _uiState.asStateFlow()

    init {
        refreshTaskList()
    }

    fun refreshTaskList() {
        viewModelScope.launch(ioDispatcher) {
            taskRepository.getTaskList().collect { taskList ->
                val todoList = taskList.filter { task -> task.taskStatus == TaskStatus.TODO }
                val inprogressList =
                    taskList.filter { task -> task.taskStatus == TaskStatus.IN_PROGRESS }
                val doneList = taskList.filter { task -> task.taskStatus == TaskStatus.DONE }
                _uiState.update {
                    it.copy(
                        taskList = taskList,
                        todoTaskList = todoList,
                        inprogressTaskList = inprogressList,
                        doneTaskList = doneList
                    )
                }
            }
        }
    }

    fun getTodoTaskList(): List<Task> = _uiState.value.todoTaskList

    fun getInprogressTaskList(): List<Task> = _uiState.value.inprogressTaskList

    fun getDoneTaskList(): List<Task> = _uiState.value.doneTaskList

    fun removeTask(task: Task) {
        viewModelScope.launch(ioDispatcher) {
            val taskToDelete = uiState.value.taskList.find { it.id == task.id }!!
            taskRepository.removeTask(taskToDelete)
        }
    }

    fun updateTaskStatus(task: Task, newStatus: TaskStatus) {
        val taskToUpdate = _uiState.value.taskList.find { it.id == task.id }
        if (taskToUpdate != null) {
            viewModelScope.launch(ioDispatcher) {
                taskRepository.updateTask(task.copy(taskStatus = newStatus))
            }
            refreshTaskList()
        }
    }


}