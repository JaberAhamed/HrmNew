package com.mall.hrmnew.ui.screens.announcement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import com.mall.hrmnew.model.domain.Announcement
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.ui.components.navigation.BottomNavScaffold
import com.mall.hrmnew.ui.theme.Spacing
import com.mall.hrmnew.viewmodel.announcement.AnnouncementViewModel

@Composable
fun AnnouncementScreen(
    viewModel: AnnouncementViewModel,
    onTabSelected: (Screen) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedAnnouncement by remember { mutableStateOf<Announcement?>(null) }

    BottomNavScaffold(
        currentScreen = Screen.Announcement,
        onTabSelected = onTabSelected
    ) { modifier ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(Spacing.Medium),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            item {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Announcements",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "${uiState.announcements.size} total",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Filter Chips
            item {
                val selectedFilter by remember { mutableStateOf("All") }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    FilterChip(
                        selected = selectedFilter == "All",
                        onClick = { },
                        label = { Text("All") }
                    )
                    FilterChip(
                        selected = selectedFilter == "Unread",
                        onClick = { },
                        label = { Text("Unread") }
                    )
                    FilterChip(
                        selected = selectedFilter == "Important",
                        onClick = { },
                        label = { Text("Important") }
                    )
                }
            }

            // Announcements List
            if (uiState.announcements.isEmpty()) {
                item {
                    com.mall.hrmnew.ui.components.common.EmptyState(
                        message = "No announcements",
                        modifier = Modifier.fillParentMaxHeight(0.3f)
                    )
                }
            } else {
                items(uiState.announcements) { announcement ->
                    AnnouncementItem(
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
            AnnouncementDetailDialog(
                announcement = announcement,
                onDismiss = { selectedAnnouncement = null }
            )
        }
    }
}

@Composable
fun AnnouncementItem(
    announcement: Announcement,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (!announcement.isRead)
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            else
                MaterialTheme.colorScheme.surface
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
                    .background(
                        color = when (announcement.priority) {
                            "High" -> MaterialTheme.colorScheme.errorContainer
                            "Medium" -> MaterialTheme.colorScheme.secondaryContainer
                            else -> MaterialTheme.colorScheme.tertiaryContainer
                        },
                        shape = CircleShape
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
                    }
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
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = announcement.priority,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnnouncementDetailDialog(
    announcement: Announcement,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(Spacing.Medium)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = when (announcement.priority) {
                            "High" -> MaterialTheme.colorScheme.errorContainer
                            "Medium" -> MaterialTheme.colorScheme.secondaryContainer
                            else -> MaterialTheme.colorScheme.tertiaryContainer
                        },
                        shape = CircleShape,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
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
                                }
                            )
                        }
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
                    style = MaterialTheme.typography.headlineSmall
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
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = announcement.category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall
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
                        modifier = Modifier.fillMaxWidth()
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close")
                }
            }
        }
    }
}
