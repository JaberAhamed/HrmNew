package com.mall.hrmnew.model.ui.dashboard

data class DashboardUiState(
    val userName: String = "John Doe",
    val isPunchedIn: Boolean = false,
    val lastPunchTime: String? = null,
    val leaveBalance: Int = 15,
    val pendingTasks: Int = 5,
    val totalVisits: Int = 12,
    val unreadNotifications: Int = 3
)
