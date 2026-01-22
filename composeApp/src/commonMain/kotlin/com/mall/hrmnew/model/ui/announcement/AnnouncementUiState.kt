package com.mall.hrmnew.model.ui.announcement

import com.mall.hrmnew.model.domain.Announcement

data class AnnouncementUiState(
    val announcements: List<Announcement> = emptyList()
)
