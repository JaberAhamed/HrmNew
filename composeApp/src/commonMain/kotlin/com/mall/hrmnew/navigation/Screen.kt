package com.mall.hrmnew.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Bottom navigation data class
 */
data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector,
    val isCenter: Boolean = false
)

sealed class Screen(val route: String) {
    // Authentication Screens
    object Splash : Screen("splash")
    object Landing : Screen("landing")
    object Login : Screen("login")
    object LocationPermission : Screen("location_permission")

    // Main Tab Screens
    object Dashboard : Screen("dashboard")
    object Attendance : Screen("attendance")
    object Leave : Screen("leave")
    object Task : Screen("task")
    object Visit : Screen("visit")
    object Announcement : Screen("announcement")
    object Other : Screen("other")
}

// Lists for iteration
val authScreens = listOf(
    Screen.Splash,
    Screen.Landing,
    Screen.Login,
    Screen.LocationPermission
)

val mainScreens = listOf(
    Screen.Dashboard,
    Screen.Attendance,
    Screen.Leave,
    Screen.Task,
    Screen.Visit,
    Screen.Announcement,
    Screen.Other
)

// Bottom navigation items (5 items with Home in center)
val bottomNavItems = listOf(
    BottomNavItem(Screen.Attendance, "Attendance", Icons.Outlined.AccessTime),
    BottomNavItem(Screen.Visit, "Visit", Icons.Outlined.Place),
    BottomNavItem(Screen.Dashboard, "Home", Icons.Outlined.Home, isCenter = true),
    BottomNavItem(Screen.Leave, "Leave", Icons.Outlined.Event),
    BottomNavItem(Screen.Other, "Other", Icons.Outlined.Menu)
)
