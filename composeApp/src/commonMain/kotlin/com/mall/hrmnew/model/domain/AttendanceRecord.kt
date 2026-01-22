package com.mall.hrmnew.model.domain

data class AttendanceRecord(
    val id: String,
    val date: String,
    val punchIn: String,
    val punchOut: String,
    val status: String // Present, Late, Absent
)

object MockAttendanceData {
    val history = listOf(
        AttendanceRecord(
            id = "1",
            date = "Jan 22, 2026",
            punchIn = "09:00 AM",
            punchOut = "06:00 PM",
            status = "Present"
        ),
        AttendanceRecord(
            id = "2",
            date = "Jan 21, 2026",
            punchIn = "09:15 AM",
            punchOut = "06:00 PM",
            status = "Late"
        ),
        AttendanceRecord(
            id = "3",
            date = "Jan 20, 2026",
            punchIn = "09:00 AM",
            punchOut = "06:00 PM",
            status = "Present"
        ),
        AttendanceRecord(
            id = "4",
            date = "Jan 19, 2026",
            punchIn = "09:00 AM",
            punchOut = "05:30 PM",
            status = "Present"
        ),
        AttendanceRecord(
            id = "5",
            date = "Jan 18, 2026",
            punchIn = "--",
            punchOut = "--",
            status = "Absent"
        )
    )
}
