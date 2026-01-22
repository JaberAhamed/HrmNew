package com.mall.hrmnew.model.ui.attendance

import com.mall.hrmnew.model.domain.AttendanceRecord

data class AttendanceUiState(
    val isPunchedIn: Boolean = false,
    val lastPunchTime: String? = null,
    val gpsEnabled: Boolean = true,
    val attendanceHistory: List<AttendanceRecord> = emptyList()
)
