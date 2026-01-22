package com.mall.hrmnew.ui.components.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.ui.theme.Spacing

/**
 * Bottom navigation data class
 */
data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)

/**
 * List of bottom navigation items
 */
val bottomNavItems = listOf(
    BottomNavItem(Screen.Dashboard, "Dashboard", Icons.Default.Home),
    BottomNavItem(Screen.Attendance, "Attendance", Icons.Default.AccessTime),
    BottomNavItem(Screen.Leave, "Leave", Icons.Default.Event),
    BottomNavItem(Screen.Task, "Task", Icons.Default.Task),
    BottomNavItem(Screen.Visit, "Visit", Icons.Default.Place),
    BottomNavItem(Screen.Announcement, "Announcement", Icons.Default.Notifications)
)

/**
 * Bottom navigation bar component
 */
@Composable
fun BottomNavigationBar(
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentScreen == item.screen
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(item.screen) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = if (selected)
                            MaterialTheme.typography.labelSmall
                        else
                            MaterialTheme.typography.labelSmall
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}

/**
 * Scaffold with bottom navigation bar
 */
@Composable
fun BottomNavScaffold(
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = currentScreen,
                onTabSelected = onTabSelected
            )
        }
    ) { paddingValues ->
        content(Modifier.padding(paddingValues))
    }
}
