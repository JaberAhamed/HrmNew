package com.mall.hrmnew.viewmodel.leave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mall.hrmnew.data.repository.LeaveRepository
import com.mall.hrmnew.model.domain.LeaveRequest
import com.mall.hrmnew.model.domain.MockLeaveData
import com.mall.hrmnew.model.ui.leave.LeaveUiState
import com.mall.hrmnew.util.getCurrentTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LeaveViewModel(
    private val leaveRepository: LeaveRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        LeaveUiState(
            leaveHistory = MockLeaveData.history
        )
    )
    val uiState: StateFlow<LeaveUiState> = _uiState.asStateFlow()

    init {
        loadLeaveBalance()
    }

    private fun loadLeaveBalance() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = leaveRepository.getLeaveBalance()

            result.fold(
                onSuccess = { response ->
                    if (response.success && response.data != null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = null,
                            leaveTotals = response.data.totals,
                            leaveBalances = response.data.leaveBalances
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = response.message ?: "Failed to load leave balance"
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load leave balance"
                    )
                }
            )
        }
    }

    fun submitLeaveRequest(
        type: String,
        startDate: String,
        endDate: String,
        reason: String
    ) {
        viewModelScope.launch {
            // Validate inputs
            if (startDate.isBlank() || endDate.isBlank() || reason.isBlank()) {
                // For UI demonstration, just don't add the request
                return@launch
            }

            // Submit request (mock)
            _uiState.value = _uiState.value.copy(
                leaveHistory = listOf(
                    LeaveRequest(
                        id = "LR${getCurrentTimestamp().take(8)}",
                        type = type,
                        startDate = startDate,
                        endDate = endDate,
                        reason = reason,
                        status = "Pending"
                    )
                ) + _uiState.value.leaveHistory
            )
        }
    }
}
