package com.mall.hrmnew.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.ui.components.navigation.BottomNavScaffold
import com.mall.hrmnew.ui.components.cards.InfoCard
import com.mall.hrmnew.ui.theme.Spacing
import com.mall.hrmnew.viewmodel.dashboard.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onTabSelected: (Screen) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    BottomNavScaffold(
        currentScreen = Screen.Dashboard,
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
                Column {
                    Text(
                        text = "Welcome back!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                    Text(
                        text = uiState.userName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.Small))
            }

            // Attendance Status Card
            item {
                AttendanceStatusCard(
                    isPunchedIn = uiState.isPunchedIn,
                    lastPunchTime = uiState.lastPunchTime,
                    onPunchIn = { viewModel.punchIn() },
                    onPunchOut = { viewModel.punchOut() }
                )
            }

            // Quick Stats
            item {
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = Spacing.Small)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    InfoCard(
                        title = "Leave Balance",
                        value = "${uiState.leaveBalance} days",
                        icon = Icons.Default.Event,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "Pending Tasks",
                        value = "${uiState.pendingTasks}",
                        icon = Icons.Default.Task,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    InfoCard(
                        title = "Total Visits",
                        value = "${uiState.totalVisits}",
                        icon = Icons.Default.Place,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "Notifications",
                        value = "${uiState.unreadNotifications}",
                        icon = Icons.Default.Notifications,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Quick Actions
            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = Spacing.Small)
                )
            }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    QuickActionCard(
                        title = "Apply Leave",
                        description = "Submit a leave request",
                        icon = Icons.Default.Event,
                        color = MaterialTheme.colorScheme.primary
                    )
                    QuickActionCard(
                        title = "View Tasks",
                        description = "Check your assigned tasks",
                        icon = Icons.Default.Task,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    QuickActionCard(
                        title = "Client Visits",
                        description = "Manage client visits",
                        icon = Icons.Default.Place,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

@Composable
fun AttendanceStatusCard(
    isPunchedIn: Boolean,
    lastPunchTime: String?,
    onPunchIn: () -> Unit,
    onPunchOut: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isPunchedIn)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(Spacing.Medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isPunchedIn) Icons.Default.CheckCircle else Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = if (isPunchedIn)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(Spacing.Small))
                    Text(
                        text = if (isPunchedIn) "Punched In" else "Not Punched In",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                if (lastPunchTime != null) {
                    Text(
                        text = lastPunchTime,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            if (isPunchedIn) {
                OutlinedButton(
                    onClick = onPunchOut,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Punch Out")
                }
            } else {
                Button(
                    onClick = onPunchIn,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Punch In")
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = color.copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(Spacing.Medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(Spacing.Medium))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = color
            )
        }
    }
}
