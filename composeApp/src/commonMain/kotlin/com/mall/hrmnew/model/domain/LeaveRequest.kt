package com.mall.hrmnew.model.domain

data class LeaveRequest(
    val id: String,
    val type: String,
    val startDate: String,
    val endDate: String,
    val reason: String,
    val status: String // Pending, Approved, Rejected, Cancelled
)

object MockLeaveData {
    val history = listOf(
        LeaveRequest(
            id = "1",
            type = "Annual",
            startDate = "2026-01-15",
            endDate = "2026-01-17",
            reason = "Family vacation",
            status = "Approved"
        ),
        LeaveRequest(
            id = "2",
            type = "Sick",
            startDate = "2026-01-10",
            endDate = "2026-01-10",
            reason = "Not feeling well",
            status = "Approved"
        ),
        LeaveRequest(
            id = "3",
            type = "Casual",
            startDate = "2026-01-25",
            endDate = "2026-01-25",
            reason = "Personal work",
            status = "Pending"
        ),
        LeaveRequest(
            id = "4",
            type = "Annual",
            startDate = "2025-12-20",
            endDate = "2025-12-25",
            reason = "Holiday trip",
            status = "Approved"
        ),
        LeaveRequest(
            id = "5",
            type = "Sick",
            startDate = "2025-12-10",
            endDate = "2025-12-11",
            reason = "Medical appointment",
            status = "Rejected"
        )
    )
}
