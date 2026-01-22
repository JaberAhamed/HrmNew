package com.mall.hrmnew.viewmodel.visit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mall.hrmnew.model.domain.MockVisitData
import com.mall.hrmnew.model.domain.Visit
import com.mall.hrmnew.model.ui.visit.VisitUiState
import com.mall.hrmnew.util.getCurrentTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VisitViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        VisitUiState(
            visits = MockVisitData.visits
        )
    )
    val uiState: StateFlow<VisitUiState> = _uiState.asStateFlow()

    fun addVisit(
        clientName: String,
        purpose: String,
        address: String,
        scheduledDate: String,
        notes: String
    ) {
        viewModelScope.launch {
            // Validate inputs
            if (clientName.isBlank() || purpose.isBlank()) {
                // For UI demonstration, just don't add the visit
                return@launch
            }

            // Add visit (mock)
            _uiState.value = _uiState.value.copy(
                visits = listOf(
                    Visit(
                        id = "V${getCurrentTimestamp().take(8)}",
                        clientName = clientName,
                        purpose = purpose,
                        address = address,
                        scheduledDate = scheduledDate,
                        status = "Scheduled",
                        notes = notes,
                        photoPath = null,
                        documentPath = null
                    )
                ) + _uiState.value.visits
            )
        }
    }
}
