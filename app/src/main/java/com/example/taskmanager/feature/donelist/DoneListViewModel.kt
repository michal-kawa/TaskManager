package com.example.taskmanager.feature.donelist

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.core.data.model.Task
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
) : ViewModel() {

    private var _uiState = MutableStateFlow(DoneListUiState())
    val uiState: StateFlow<DoneListUiState> = _uiState.asStateFlow()

    init {
        refreshTaskList()
    }

    private fun refreshTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(listOfTasks = taskRepository.getDoneTaskList()) }
        }
    }
}