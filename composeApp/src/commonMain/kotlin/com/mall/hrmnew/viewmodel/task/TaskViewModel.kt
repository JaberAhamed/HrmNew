package com.mall.hrmnew.viewmodel.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mall.hrmnew.model.domain.MockTaskData
import com.mall.hrmnew.model.domain.Task
import com.mall.hrmnew.model.ui.task.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        TaskUiState(
            tasks = MockTaskData.tasks
        )
    )
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    fun updateTaskStatus(taskId: String, newStatus: String) {
        viewModelScope.launch {
            val updatedTasks = _uiState.value.tasks.map { task ->
                if (task.id == taskId) {
                    task.copy(status = newStatus)
                } else {
                    task
                }
            }
            _uiState.value = _uiState.value.copy(tasks = updatedTasks)
        }
    }
}
