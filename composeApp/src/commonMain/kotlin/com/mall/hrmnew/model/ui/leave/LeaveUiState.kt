package com.mall.hrmnew.model.ui.leave

import com.mall.hrmnew.model.domain.LeaveRequest

data class LeaveUiState(
    val annualLeaveBalance: Int = 15,
    val sickLeaveBalance: Int = 10,
    val casualLeaveBalance: Int = 7,
    val leaveHistory: List<LeaveRequest> = emptyList()
)
