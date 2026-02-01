package com.mall.hrmnew.model.ui.leave

import com.mall.hrmnew.data.model.dto.LeaveBalance
import com.mall.hrmnew.data.model.dto.LeaveTotals

data class LeaveUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val leaveTotals: LeaveTotals? = null,
    val leaveBalances: List<LeaveBalance> = emptyList()
)
