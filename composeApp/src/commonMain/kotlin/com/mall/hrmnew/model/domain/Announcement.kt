package com.mall.hrmnew.model.domain

data class Announcement(
    val id: String,
    val title: String,
    val content: String,
    val category: String, // HR, Company, Event, General
    val priority: String, // High, Medium, Low
    val sender: String,
    val date: String,
    val isRead: Boolean = false,
    val attachmentUrl: String? = null
)

object MockAnnouncementData {
    val announcements = listOf(
        Announcement(
            id = "1",
            title = "Office Closure Notice",
            content = "The office will be closed on January 26th for Republic Day. Regular operations will resume on January 27th.",
            category = "Company",
            priority = "High",
            sender = "HR Department",
            date = "Jan 22, 2026",
            isRead = false,
            attachmentUrl = null
        ),
        Announcement(
            id = "2",
            title = "Q1 Performance Reviews",
            content = "Quarterly performance reviews will be held from February 1st to February 15th. Please schedule your meeting with your manager.",
            category = "HR",
            priority = "High",
            sender = "HR Department",
            date = "Jan 21, 2026",
            isRead = false,
            attachmentUrl = null
        ),
        Announcement(
            id = "3",
            title = "New Health Benefits",
            content = "We are pleased to announce enhanced health benefits starting from March 1st. Details have been emailed to everyone.",
            category = "HR",
            priority = "Medium",
            sender = "HR Department",
            date = "Jan 20, 2026",
            isRead = true,
            attachmentUrl = "benefits.pdf"
        ),
        Announcement(
            id = "4",
            title = "Team Building Event",
            content = "Join us for a team building event on February 5th at the City Park. More details to follow.",
            category = "Event",
            priority = "Medium",
            sender = "Events Team",
            date = "Jan 19, 2026",
            isRead = false,
            attachmentUrl = null
        ),
        Announcement(
            id = "5",
            title = "System Maintenance",
            content = "IT systems will undergo maintenance on January 27th from 10 PM to 2 AM. Please save your work before leaving.",
            category = "Company",
            priority = "Low",
            sender = "IT Department",
            date = "Jan 18, 2026",
            isRead = true,
            attachmentUrl = null
        ),
        Announcement(
            id = "6",
            title = "New Cafeteria Menu",
            content = "The cafeteria has updated its menu with new healthy options starting next week.",
            category = "General",
            priority = "Low",
            sender = "Admin",
            date = "Jan 17, 2026",
            isRead = true,
            attachmentUrl = "menu.pdf"
        ),
        Announcement(
            id = "7",
            title = "Training Program Registration",
            content = "Registration is now open for the 'Leadership Skills' training program. Limited seats available.",
            category = "HR",
            priority = "Medium",
            sender = "Learning & Development",
            date = "Jan 16, 2026",
            isRead = false,
            attachmentUrl = null
        )
    )
}
