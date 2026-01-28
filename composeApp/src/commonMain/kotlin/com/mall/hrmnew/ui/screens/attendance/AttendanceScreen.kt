package com.mall.hrmnew.ui.screens.attendance

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mall.hrmnew.model.domain.AttendanceRecord
import com.mall.hrmnew.ui.theme.Spacing
import com.mall.hrmnew.viewmodel.attendance.AttendanceViewModel
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Clock.System
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    viewModel: AttendanceViewModel,
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Attendance") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
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
                .padding(horizontal = Spacing.Medium),
            contentPadding = PaddingValues(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            // Attendance Calendar
            item {
                AttendanceCalendar(
                    attendanceRecords = uiState.attendanceHistory
                )
            }

            // Attendance History Header
            item {
                Text(
                    text = "Attendance History",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(uiState.attendanceHistory) { record ->
                ModernAttendanceHistoryItem(record = record)
            }
        }
    }
}

@Composable
fun ModernAttendanceHistoryItem(record: AttendanceRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(Spacing.Small))
                Column {
                    Text(
                        text = record.date,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${record.punchIn} - ${record.punchOut}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Surface(
                color = when (record.status) {
                    "Present" -> MaterialTheme.colorScheme.primaryContainer
                    "Late" -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = record.status,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceCalendar(
    attendanceRecords: List<AttendanceRecord>
) {
    var currentMonth by remember {
        mutableStateOf(
            System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
        )
    }

    // Create a map of dates to attendance status
    val attendanceMap = remember(attendanceRecords) {
        attendanceRecords.associate { record ->
            parseDateKey(record.date) to record.status
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(top = Spacing.Medium),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Large)
        ) {
            // Month Navigation Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Spacing.Medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        currentMonth = currentMonth.minus(DatePeriod(months = 1))
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                        contentDescription = "Previous month",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "${currentMonth.month.name} ${currentMonth.year}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = {
                        currentMonth = currentMonth.plus(DatePeriod(months = 1))
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = "Next month",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Day of Week Headers
            val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Spacing.Small),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                daysOfWeek.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Calendar Grid
            val firstDayOfMonth = getFirstDayOfMonth(currentMonth)
            val daysInMonth = getDaysInMonth(currentMonth)
            val today = System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date

            // Generate calendar weeks
            val totalCells = firstDayOfMonth + daysInMonth
            val numRows = (totalCells + 6) / 7

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall)
            ) {
                repeat(numRows) { weekIndex ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(7) { dayOfWeek ->
                            val dayIndex = weekIndex * 7 + dayOfWeek
                            if (dayIndex < firstDayOfMonth) {
                                // Empty cell for days before month starts
                                Spacer(modifier = Modifier.weight(1f))
                            } else if (dayIndex - firstDayOfMonth < daysInMonth) {
                                // Valid day of the month
                                val day = dayIndex - firstDayOfMonth + 1
                                val date = kotlinx.datetime.LocalDate(currentMonth.year, currentMonth.month, day)
                                val dateKey = formatDateKey(date)
                                val status = attendanceMap[dateKey]
                                val isToday = date == today

                                Box(modifier = Modifier.weight(1f)) {
                                    CalendarDayCell(
                                        day = day,
                                        status = status,
                                        isToday = isToday
                                    )
                                }
                            } else {
                                // Empty cell for days after month ends
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // Legend
            Spacer(modifier = Modifier.height(Spacing.Medium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LegendItem(
                    color = MaterialTheme.colorScheme.primary,
                    label = "Present"
                )
                LegendItem(
                    color = MaterialTheme.colorScheme.error,
                    label = "Late"
                )
                LegendItem(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    label = "Absent"
                )
            }
        }
    }
}

@Composable
fun CalendarDayCell(
    day: Int,
    status: String?,
    isToday: Boolean
) {
    val backgroundColor = when (status) {
        "Present" -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        "Late" -> MaterialTheme.colorScheme.error.copy(alpha = 0.15f)
        "Absent" -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        else -> Color.Transparent
    }

    val borderColor = when (status) {
        "Present" -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        "Late" -> MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
        "Absent" -> MaterialTheme.colorScheme.surfaceVariant
        else -> if (isToday) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.Transparent
    }

    val textColor = when (status) {
        "Present" -> MaterialTheme.colorScheme.primary
        "Late" -> MaterialTheme.colorScheme.error
        "Absent" -> MaterialTheme.colorScheme.onSurfaceVariant
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .height(40.dp)
            .aspectRatio(1f)
            .background(backgroundColor, CircleShape)
            .border(
                width = if (isToday || status != null) 1.dp else 0.dp,
                color = borderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LegendItem(
    color: Color,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color.copy(alpha = 0.5f), CircleShape)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Helper functions
fun getFirstDayOfMonth(date: kotlinx.datetime.LocalDate): Int {
    val firstDay = kotlinx.datetime.LocalDate(date.year, date.month, 1)
    return firstDay.dayOfWeek.ordinal % 7
}

fun getDaysInMonth(date: kotlinx.datetime.LocalDate): Int {
    return when (date.month) {
        Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> if (isLeapYear(date.year)) 29 else 28
    }
}

fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
}

fun parseDateKey(dateString: String): String {
    // Parse "Jan 22, 2026" format to "2026-01-22"
    val parts = dateString.split(" ")
    if (parts.size == 3) {
        val month = parseMonth(parts[0])
        val day = parts[1].replace(",", "").padStart(2, '0')
        val year = parts[2]
        return "$year-$month-$day"
    }
    return dateString
}

fun formatDateKey(date: kotlinx.datetime.LocalDate): String {
    return "${date.year}-${date.monthNumber.toString().padStart(2, '0')}-${date.dayOfMonth.toString().padStart(2, '0')}"
}

fun parseMonth(monthAbbr: String): String {
    return when (monthAbbr.uppercase()) {
        "JAN" -> "01"
        "FEB" -> "02"
        "MAR" -> "03"
        "APR" -> "04"
        "MAY" -> "05"
        "JUN" -> "06"
        "JUL" -> "07"
        "AUG" -> "08"
        "SEP" -> "09"
        "OCT" -> "10"
        "NOV" -> "11"
        "DEC" -> "12"
        else -> "01"
    }
}
