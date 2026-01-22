package com.mall.hrmnew.model.ui.task

import com.mall.hrmnew.model.domain.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList()
)
