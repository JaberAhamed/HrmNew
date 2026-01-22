package com.mall.hrmnew.model.domain

data class Visit(
    val id: String,
    val clientName: String,
    val purpose: String,
    val address: String,
    val scheduledDate: String,
    val status: String, // Scheduled, Completed, Cancelled
    val notes: String,
    val photoPath: String? = null,
    val documentPath: String? = null
)

object MockVisitData {
    val visits = listOf(
        Visit(
            id = "1",
            clientName = "ABC Corporation",
            purpose = "Product Demo",
            address = "123 Business Ave, New York",
            scheduledDate = "2026-01-25",
            status = "Scheduled",
            notes = "Prepare demo materials",
            photoPath = null,
            documentPath = null
        ),
        Visit(
            id = "2",
            clientName = "XYZ Industries",
            purpose = "Contract Signing",
            address = "456 Industry Blvd, Los Angeles",
            scheduledDate = "2026-01-24",
            status = "Completed",
            notes = "Contract signed successfully",
            photoPath = null,
            documentPath = null
        ),
        Visit(
            id = "3",
            clientName = "Tech Solutions Ltd",
            purpose = "Technical Support",
            address = "789 Tech Street, San Francisco",
            scheduledDate = "2026-01-23",
            status = "Completed",
            notes = "Issue resolved",
            photoPath = null,
            documentPath = null
        ),
        Visit(
            id = "4",
            clientName = "Global Ventures",
            purpose = "Quarterly Review",
            address = "321 Global Way, Chicago",
            scheduledDate = "2026-01-28",
            status = "Scheduled",
            notes = "Prepare Q4 report",
            photoPath = null,
            documentPath = null
        ),
        Visit(
            id = "5",
            clientName = "StartUp Inc",
            purpose = "Partnership Discussion",
            address = "654 Innovation Drive, Boston",
            scheduledDate = "2026-01-22",
            status = "Completed",
            notes = "Partnership agreed",
            photoPath = null,
            documentPath = null
        )
    )
}
