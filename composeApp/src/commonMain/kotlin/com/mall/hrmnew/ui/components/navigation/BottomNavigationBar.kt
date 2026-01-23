package com.mall.hrmnew.ui.components.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.ui.theme.Spacing
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

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
    BottomNavItem(Screen.Dashboard, "Dashboard", Icons.Outlined.Home),
    BottomNavItem(Screen.Attendance, "Attendance", Icons.Outlined.AccessTime),
    BottomNavItem(Screen.Leave, "Leave", Icons.Outlined.Event),
    BottomNavItem(Screen.Task, "Task", Icons.Outlined.Task),
    BottomNavItem(Screen.Visit, "Visit", Icons.Outlined.Place),
    BottomNavItem(Screen.Announcement, "Announcement", Icons.Outlined.Notifications)
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

/**
 * Navigation drawer menu item
 */
@Composable
fun DrawerMenuItem(
    screen: Screen,
    label: String,
    icon: ImageVector,
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit,
    onDrawerClose: () -> Unit
) {
    val selected = currentScreen == screen
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        selected = selected,
        onClick = {
            onScreenSelected(screen)
            onDrawerClose()
        },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,

        ),
        modifier = Modifier.padding(horizontal = Spacing.Small)
    )
}

/**
 * Navigation drawer component
 */
@Composable
fun AppNavigationDrawer(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit,
    onDrawerClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(Spacing.Medium))

                // Header
                Text(
                    text = "HRM App",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(Spacing.Medium)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = Spacing.Small)
                )

                // Menu Items
                bottomNavItems.forEach { item ->
                    DrawerMenuItem(
                        screen = item.screen,
                        label = item.label,
                        icon = item.icon,
                        currentScreen = currentScreen,
                        onScreenSelected = onScreenSelected,
                        onDrawerClose = onDrawerClose
                    )
                }
            }
        }
    ) {
        // Content will be provided by Scaffold
    }
}

/**
 * Scaffold with navigation drawer
 */
@Composable
fun NavDrawerScaffold(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(Spacing.Medium))

                // Header
                Text(
                    text = "HRM App",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(Spacing.Medium)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = Spacing.Small)
                )

                // Menu Items
                bottomNavItems.forEach { item ->
                    val selected = currentScreen == item.screen
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        selected = selected,
                        onClick = {
                            onScreenSelected(item.screen)
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.secondary.copy(0.3f)
                        ),
                        modifier = Modifier.padding(horizontal = Spacing.Small)
                    )
                }
            }
        }
    ) {
        content()
    }
}
