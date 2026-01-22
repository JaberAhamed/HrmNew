package com.mall.hrmnew.viewmodel.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mall.hrmnew.model.ui.dashboard.DashboardUiState
import com.mall.hrmnew.util.getCurrentTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

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
