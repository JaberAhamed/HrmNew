package com.mall.hrmnew.ui.screens.visit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import com.mall.hrmnew.model.domain.Visit
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.ui.components.navigation.BottomNavScaffold
import com.mall.hrmnew.ui.components.buttons.PrimaryButton
import com.mall.hrmnew.ui.components.buttons.SecondaryButton
import com.mall.hrmnew.ui.theme.Spacing
import com.mall.hrmnew.viewmodel.visit.VisitViewModel

@Composable
fun VisitScreen(
    viewModel: VisitViewModel,
    onTabSelected: (Screen) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddVisitDialog by remember { mutableStateOf(false) }

    BottomNavScaffold(
        currentScreen = Screen.Visit,
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
                        text = "Client Visits",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "${uiState.visits.size} visits",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Stats Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    val totalVisits = uiState.visits.size
                    val completedVisits = uiState.visits.count { it.status == "Completed" }
                    val scheduledVisits = uiState.visits.count { it.status == "Scheduled" }

                    VisitStatCard(
                        label = "Total",
                        count = totalVisits,
                        icon = Icons.Default.Place,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.primary
                    )
                    VisitStatCard(
                        label = "Completed",
                        count = completedVisits,
                        icon = Icons.Default.CheckCircle,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    VisitStatCard(
                        label = "Pending",
                        count = scheduledVisits,
                        icon = Icons.Default.Schedule,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            // Add Visit Button
            item {
                PrimaryButton(
                    text = "Add New Visit",
                    onClick = { showAddVisitDialog = true }
                )
            }

            // Visits List
            item {
                Text(
                    text = "Recent Visits",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (uiState.visits.isEmpty()) {
                item {
                    com.mall.hrmnew.ui.components.common.EmptyState(
                        message = "No visits scheduled",
                        modifier = Modifier.fillParentMaxHeight(0.3f)
                    )
                }
            } else {
                items(uiState.visits) { visit ->
                    VisitItem(
                        visit = visit
                    )
                }
            }
        }

        if (showAddVisitDialog) {
            AddVisitDialog(
                onDismiss = { showAddVisitDialog = false },
                onSubmit = { client, purpose, address, date, notes ->
                    viewModel.addVisit(client, purpose, address, date, notes)
                    showAddVisitDialog = false
                }
            )
        }
    }
}

@Composable
fun VisitStatCard(
    label: String,
    count: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(Spacing.Small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
            Text(
                text = "$count",
                style = MaterialTheme.typography.titleMedium,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
    }
}

@Composable
fun VisitItem(
    visit: Visit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.Medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = visit.clientName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = visit.purpose,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = visit.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Scheduled: ${visit.scheduledDate}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    color = when (visit.status) {
                        "Completed" -> MaterialTheme.colorScheme.primaryContainer
                        "Scheduled" -> MaterialTheme.colorScheme.secondaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = visit.status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

        }
    }
}

@Composable
fun AddVisitDialog(
    onDismiss: () -> Unit,
    onSubmit: (client: String, purpose: String, address: String, date: String, notes: String) -> Unit
) {
    var clientName by remember { mutableStateOf("") }
    var purpose by remember { mutableStateOf("") }
    var scheduledDate by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(Spacing.Medium)
            ) {
                Text(
                    text = "Add New Visit",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(Spacing.Medium))

                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Client Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Spacing.Small))

                OutlinedTextField(
                    value = purpose,
                    onValueChange = { purpose = it },
                    label = { Text("Purpose") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Spacing.Small))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Spacing.Small))

                OutlinedTextField(
                    value = scheduledDate,
                    onValueChange = { scheduledDate = it },
                    label = { Text("Scheduled Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Spacing.Small))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(Spacing.Small))

                // Placeholder for photo capture
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Spacing.Small))
                    Text("Attach Photo (Placeholder)")
                }

                Spacer(modifier = Modifier.height(Spacing.Small))

                // Placeholder for document
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Spacing.Small))
                    Text("Attach Document (Placeholder)")
                }

                Spacer(modifier = Modifier.height(Spacing.Medium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    SecondaryButton(
                        text = "Cancel",
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    )
                    PrimaryButton(
                        text = "Submit",
                        onClick = {
                            onSubmit(clientName, purpose, address, scheduledDate, notes)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
