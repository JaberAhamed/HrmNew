package com.mall.hrmnew.viewmodel.leave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mall.hrmnew.data.repository.LeaveRepository
import com.mall.hrmnew.model.ui.leave.LeaveUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LeaveViewModel(
    private val leaveRepository: LeaveRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LeaveUiState())
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
}
