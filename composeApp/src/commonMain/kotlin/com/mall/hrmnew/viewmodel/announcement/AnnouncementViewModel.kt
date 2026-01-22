package com.mall.hrmnew.viewmodel.announcement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mall.hrmnew.model.domain.Announcement
import com.mall.hrmnew.model.domain.MockAnnouncementData
import com.mall.hrmnew.model.ui.announcement.AnnouncementUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnnouncementViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        AnnouncementUiState(
            announcements = MockAnnouncementData.announcements
        )
    )
    val uiState: StateFlow<AnnouncementUiState> = _uiState.asStateFlow()

    fun markAsRead(announcementId: String) {
        viewModelScope.launch {
            val updatedAnnouncements = _uiState.value.announcements.map { announcement ->
                if (announcement.id == announcementId) {
                    announcement.copy(isRead = true)
                } else {
                    announcement
                }
            }
            _uiState.value = _uiState.value.copy(
                announcements = updatedAnnouncements
            )
        }
    }
}
