package com.mall.hrmnew.model.ui.visit

import com.mall.hrmnew.model.domain.Visit

data class VisitUiState(
    val visits: List<Visit> = emptyList()
)
