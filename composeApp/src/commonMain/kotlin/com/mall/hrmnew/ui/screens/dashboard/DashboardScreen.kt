package com.mall.hrmnew.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.*
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mall.hrmnew.ui.theme.LocalAppColor
import com.mall.hrmnew.ui.theme.Spacing
import com.mall.hrmnew.viewmodel.dashboard.DashboardViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onMenuClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dashboard") },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open navigation drawer"
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
                // Welcome Header
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Welcome back!",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                            Text(
                                text = uiState.userName,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF3498DB),
                                            Color(0xFF20B2AA)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
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
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = Spacing.Small)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    ModernInfoCard(
                        title = "Leave Balance",
                        value = "${uiState.leaveBalance} days",
                        icon = Icons.Outlined.Event,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    ModernInfoCard(
                        title = "Pending Tasks",
                        value = "${uiState.pendingTasks}",
                        icon = Icons.Outlined.Task,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    ModernInfoCard(
                        title = "Total Visits",
                        value = "${uiState.totalVisits}",
                        icon = Icons.Outlined.Place,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.weight(1f)
                    )
                    ModernInfoCard(
                        title = "Notifications",
                        value = "${uiState.unreadNotifications}",
                        icon = Icons.Outlined.Notifications,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Quick Actions
            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = Spacing.Small)
                )
            }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    ModernQuickActionCard(
                        title = "Apply Leave",
                        description = "Submit a leave request",
                        icon = Icons.Outlined.Event,
                        color = LocalAppColor.current.green
                    )
                    ModernQuickActionCard(
                        title = "View Tasks",
                        description = "Check your assigned tasks",
                        icon = Icons.Outlined.Task,
                        color = MaterialTheme.colorScheme.primary
                    )
                    ModernQuickActionCard(
                        title = "Client Visits",
                        description = "Manage client visits",
                        icon = Icons.Outlined.Place,
                        color = LocalAppColor.current.orange
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
    val infiniteTransition = rememberInfiniteTransition(label = "statusPulse")

    // Subtle pulse for status indicator
    val statusPulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "statusPulse"
    )

    // Card background animation
    val cardColor by animateColorAsState(
        targetValue = if (isPunchedIn)
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        else
            MaterialTheme.colorScheme.surface,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "cardColor"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Crossfade(
                        targetState = isPunchedIn,
                        animationSpec = tween(500, easing = FastOutSlowInEasing),
                        label = "statusText"
                    ) { punchedIn ->
                        Text(
                            text = if (punchedIn) "Punched In" else "Not Punched In",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (punchedIn)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }

                    if (lastPunchTime != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = lastPunchTime,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Status indicator dot
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(
                            if (isPunchedIn)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        .graphicsLayer {
                            scaleX = statusPulseScale
                            scaleY = statusPulseScale
                        }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Circular Progress Button
            Crossfade(
                targetState = isPunchedIn,
                animationSpec = tween(500, easing = FastOutSlowInEasing),
                label = "buttonSwitch"
            ) { punchedIn ->
                if (punchedIn) {
                    CircularProgressPunchButton(
                        text = "Punch Out",
                        icon = Icons.Outlined.CheckCircle,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        progressColor = MaterialTheme.colorScheme.error,
                        onComplete = onPunchOut
                    )
                } else {
                    CircularProgressPunchButton(
                        text = "Punch In",
                        icon = Icons.Outlined.AccessTime,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        progressColor = MaterialTheme.colorScheme.primary,
                        onComplete = onPunchIn
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Instruction text
            Text(
                text = "Hold to confirm",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CircularProgressPunchButton(
    text: String,
    icon: ImageVector,
    containerColor: Color,
    progressColor: Color,
    onComplete: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }
    var isPressed by remember { mutableStateOf(false) }

    val progressAnim by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 50, easing = LinearEasing),
        label = "progressAnim"
    )

    // Reset animation when released
    val resetAnim by animateFloatAsState(
        targetValue = if (isPressed) 0f else 0f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "resetAnim"
    )

    // Scale animation for completion feedback
    val completeScale by animateFloatAsState(
        targetValue = if (progressAnim >= 0.99f) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "completeScale"
    )

    // Rotation animation
    val rotationAngle by animateFloatAsState(
        targetValue = progressAnim * 360f,
        animationSpec = tween(durationMillis = 50, easing = LinearEasing),
        label = "rotationAngle"
    )

    LaunchedEffect(isPressed) {
        if (isPressed) {
            while (progress < 1f && isPressed) {
                progress += 0.02f
                delay(30)
            }
            if (progress >= 1f) {
                onComplete()
                progress = 0f
            }
        } else {
            progress = 0f
        }
    }

    Box(
        modifier = Modifier
            .size(120.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        isPressed = true
                        try {
                            awaitRelease()
                        } finally {
                            isPressed = false
                        }
                    }
                )
            }
            .graphicsLayer {
                scaleX = completeScale
                scaleY = completeScale
            },
        contentAlignment = Alignment.Center
    ) {
        // Background circle
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(containerColor)
        )

        // Progress ring
        Canvas(
            modifier = Modifier.size(120.dp)
        ) {
            val strokeWidth = 4.dp.toPx()
            val radius = (size.minDimension / 2) - (strokeWidth / 2)

            drawCircle(
                color = progressColor,
                radius = radius,
                style = Stroke(width = strokeWidth)
            )

            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = progressAnim * 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Inner content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                ),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = progressColor,
                modifier = Modifier
                    .size(32.dp)
                    .graphicsLayer {
                        rotationZ = if (isPressed) rotationAngle else 0f
                    }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = progressColor,
                fontSize = 11.sp
            )
        }

        // Progress percentage display when pressing
        if (isPressed && progress > 0) {
            Text(
                text = "${(progressAnim * 100).toInt()}%",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = progressColor,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )
        }

        // Completion feedback - success animation
        if (progressAnim >= 0.99f) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        progressColor.copy(alpha = 0.2f)
                    )
            )
        }
    }
}

@Composable
fun ModernInfoCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),

    ) {
        Column(
            modifier = Modifier.padding(Spacing.Medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(Spacing.Small))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ModernQuickActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = color.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(Spacing.Medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(Spacing.Medium))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = color
            )
        }
    }
}
