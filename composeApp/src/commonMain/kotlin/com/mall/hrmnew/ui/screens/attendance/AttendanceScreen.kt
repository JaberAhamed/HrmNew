package com.mall.hrmnew.ui.screens.attendance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mall.hrmnew.model.domain.AttendanceRecord
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.ui.components.navigation.BottomNavScaffold
import com.mall.hrmnew.ui.theme.Spacing
import com.mall.hrmnew.viewmodel.attendance.AttendanceViewModel

@Composable
fun AttendanceScreen(
    viewModel: AttendanceViewModel,
    onTabSelected: (Screen) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    BottomNavScaffold(
        currentScreen = Screen.Attendance,
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
                Text(
                    text = "Attendance",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            // Current Status Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (uiState.isPunchedIn)
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
                            Column {
                                Text(
                                    text = "Today's Status",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = if (uiState.isPunchedIn) "Punched In" else "Not Punched In",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (uiState.isPunchedIn)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(
                                imageVector = if (uiState.isPunchedIn)
                                    Icons.Default.CheckCircle
                                else
                                    Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = if (uiState.isPunchedIn)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (uiState.lastPunchTime != null) {
                            Spacer(modifier = Modifier.height(Spacing.Small))
                            Text(
                                text = "Last punch: ${uiState.lastPunchTime}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(Spacing.Medium))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                        ) {
                            if (uiState.isPunchedIn) {
                                OutlinedButton(
                                    onClick = { viewModel.punchOut() },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(Spacing.ExtraSmall))
                                    Text("Punch Out")
                                }
                            } else {
                                Button(
                                    onClick = { viewModel.punchIn() },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Login,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(Spacing.ExtraSmall))
                                    Text("Punch In")
                                }
                            }
                        }
                    }
                }
            }

            // GPS Status
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (uiState.gpsEnabled)
                            MaterialTheme.colorScheme.secondaryContainer
                        else
                            MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.Medium),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = if (uiState.gpsEnabled)
                                MaterialTheme.colorScheme.secondary
                            else
                                MaterialTheme.colorScheme.error
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "GPS Location",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = if (uiState.gpsEnabled) "Enabled" else "Disabled - Please enable GPS",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (uiState.gpsEnabled)
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                else
                                    MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                        if (uiState.gpsEnabled) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }

            // Attendance History
            item {
                Text(
                    text = "Attendance History",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(uiState.attendanceHistory) { record ->
                AttendanceHistoryItem(record = record)
            }
        }
    }
}

@Composable
fun AttendanceHistoryItem(record: AttendanceRecord) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.date,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${record.punchIn} - ${record.punchOut}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Surface(
                color = when (record.status) {
                    "Present" -> MaterialTheme.colorScheme.primaryContainer
                    "Late" -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                },
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = record.status,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
