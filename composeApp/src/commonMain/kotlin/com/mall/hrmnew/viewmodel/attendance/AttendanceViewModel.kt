package com.mall.hrmnew.viewmodel.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mall.hrmnew.model.domain.MockAttendanceData
import com.mall.hrmnew.model.ui.attendance.AttendanceUiState
import com.mall.hrmnew.util.getCurrentTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttendanceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        AttendanceUiState(
            attendanceHistory = MockAttendanceData.history
        )
    )
    val uiState: StateFlow<AttendanceUiState> = _uiState.asStateFlow()

    fun punchIn() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isPunchedIn = true,
                lastPunchTime = getCurrentTimestamp()
            )
        }
    }

    fun punchOut() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isPunchedIn = false,
                lastPunchTime = null
            )
        }
    }
}
