package com.mall.hrmnew.ui.screens.announcement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import com.mall.hrmnew.model.domain.Announcement
import com.mall.hrmnew.ui.theme.Spacing
import com.mall.hrmnew.viewmodel.announcement.AnnouncementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementScreen(
    viewModel: AnnouncementViewModel,
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedAnnouncement by remember { mutableStateOf<Announcement?>(null) }
    var selectedFilter by remember { mutableStateOf("All") }


    val filteredAnnouncements = remember(selectedFilter, uiState.announcements) {
        when (selectedFilter) {
            "Unread" -> uiState.announcements.filter { !it.isRead }
            "Important" -> uiState.announcements.filter { it.priority == "High" }
            else -> uiState.announcements
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Announcements") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { topBarPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(topBarPadding)
                .padding(Spacing.Medium),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            item {
                // Header Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.Large),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Announcements",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                            Text(
                                text = "${filteredAnnouncements.size} total",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }

            // Filter Chips
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    FilterChip(
                        selected = selectedFilter == "All",
                        onClick = { selectedFilter = "All" },
                        label = { Text("All") },
                        shape = RoundedCornerShape(12.dp)
                    )
                    FilterChip(
                        selected = selectedFilter == "Unread",
                        onClick = { selectedFilter = "Unread" },
                        label = { Text("Unread") },
                        shape = RoundedCornerShape(12.dp)
                    )
                    FilterChip(
                        selected = selectedFilter == "Important",
                        onClick = { selectedFilter = "Important" },
                        label = { Text("Important") },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            // Announcements List Header
            item {
                Text(
                    text = "All Announcements",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Announcements List
            if (filteredAnnouncements.isEmpty()) {
                item {
                    com.mall.hrmnew.ui.components.common.EmptyState(
                        message = "No announcements",
                        modifier = Modifier.fillParentMaxHeight(0.3f)
                    )
                }
            } else {
                items(filteredAnnouncements) { announcement ->
                    ModernAnnouncementItem(
                        announcement = announcement,
                        onClick = {
                            selectedAnnouncement = announcement
                            viewModel.markAsRead(announcement.id)
                        }
                    )
                }
            }
        }

        selectedAnnouncement?.let { announcement ->
            ModernAnnouncementDetailDialog(
                announcement = announcement,
                onDismiss = { selectedAnnouncement = null }
            )
        }
    }
}

@Composable
fun ModernAnnouncementItem(
    announcement: Announcement,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!announcement.isRead)
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            else
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Medium),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        color = when (announcement.priority) {
                            "High" -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                            "Medium" -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                            else -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (announcement.category) {
                        "HR" -> Icons.Default.People
                        "Company" -> Icons.Default.Business
                        "Event" -> Icons.Default.Event
                        else -> Icons.Default.Notifications
                    },
                    contentDescription = null,
                    tint = when (announcement.priority) {
                        "High" -> MaterialTheme.colorScheme.error
                        "Medium" -> MaterialTheme.colorScheme.secondary
                        else -> MaterialTheme.colorScheme.tertiary
                    },
                    modifier = Modifier.size(24.dp)
                )
            }

            // Content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = announcement.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (!announcement.isRead) {
                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape,
                            modifier = Modifier.size(8.dp)
                        ) {}
                    }
                }

                Text(
                    text = announcement.content,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = announcement.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Surface(
                        color = when (announcement.priority) {
                            "High" -> MaterialTheme.colorScheme.error
                            "Medium" -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = announcement.priority,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModernAnnouncementDetailDialog(
    announcement: Announcement,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(Spacing.Large)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                when (announcement.priority) {
                                    "High" -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                                    "Medium" -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                                    else -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (announcement.category) {
                                "HR" -> Icons.Default.People
                                "Company" -> Icons.Default.Business
                                "Event" -> Icons.Default.Event
                                else -> Icons.Default.Notifications
                            },
                            contentDescription = null,
                            tint = when (announcement.priority) {
                                "High" -> MaterialTheme.colorScheme.error
                                "Medium" -> MaterialTheme.colorScheme.secondary
                                else -> MaterialTheme.colorScheme.tertiary
                            },
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.Medium))

                // Title
                Text(
                    text = announcement.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(Spacing.Small))

                // Metadata
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = announcement.category,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Text(
                        text = announcement.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "By ${announcement.sender}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.Medium))

                // Content
                Text(
                    text = announcement.content,
                    style = MaterialTheme.typography.bodyMedium
                )

                if (announcement.attachmentUrl != null) {
                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AttachFile,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(Spacing.Small))
                        Text("View Attachment (Placeholder)")
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.Medium))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Close", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
