package com.mall.hrmnew.model.domain

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val priority: String, // High, Medium, Low
    val status: String, // Pending, In Progress, Completed
    val dueDate: String,
    val assignedBy: String
)

object MockTaskData {
    val tasks = listOf(
        Task(
            id = "1",
            title = "Complete Q1 Report",
            description = "Prepare and submit the Q1 sales report",
            priority = "High",
            status = "In Progress",
            dueDate = "2026-01-25",
            assignedBy = "John Smith"
        ),
        Task(
            id = "2",
            title = "Client Presentation",
            description = "Prepare presentation for upcoming client meeting",
            priority = "High",
            status = "Pending",
            dueDate = "2026-01-28",
            assignedBy = "Sarah Johnson"
        ),
        Task(
            id = "3",
            title = "Update Documentation",
            description = "Update API documentation with new endpoints",
            priority = "Medium",
            status = "In Progress",
            dueDate = "2026-01-30",
            assignedBy = "Mike Wilson"
        ),
        Task(
            id = "4",
            title = "Code Review",
            description = "Review pull requests for the new feature",
            priority = "Medium",
            status = "Pending",
            dueDate = "2026-01-24",
            assignedBy = "Emily Brown"
        ),
        Task(
            id = "5",
            title = "Team Meeting Preparation",
            description = "Prepare agenda for weekly team meeting",
            priority = "Low",
            status = "Completed",
            dueDate = "2026-01-20",
            assignedBy = "David Lee"
        ),
        Task(
            id = "6",
            title = "Fix Login Bug",
            description = "Investigate and fix the authentication issue",
            priority = "High",
            status = "Pending",
            dueDate = "2026-01-23",
            assignedBy = "John Smith"
        ),
        Task(
            id = "7",
            title = "Database Optimization",
            description = "Optimize database queries for better performance",
            priority = "Medium",
            status = "Pending",
            dueDate = "2026-02-01",
            assignedBy = "Sarah Johnson"
        ),
        Task(
            id = "8",
            title = "Update User Guide",
            description = "Update the user guide with new features",
            priority = "Low",
            status = "Completed",
            dueDate = "2026-01-18",
            assignedBy = "Mike Wilson"
        )
    )
}
