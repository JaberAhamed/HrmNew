package com.mall.hrmnew.navigation

sealed class Screen(val route: String) {
    // Authentication Screens
    object Splash : Screen("splash")
    object Landing : Screen("landing")
    object Login : Screen("login")

    // Main Tab Screens
    object Dashboard : Screen("dashboard")
    object Attendance : Screen("attendance")
    object Leave : Screen("leave")
    object Task : Screen("task")
    object Visit : Screen("visit")
    object Announcement : Screen("announcement")
}

// Lists for iteration
val authScreens = listOf(
    Screen.Splash,
    Screen.Landing,
    Screen.Login
)

val mainScreens = listOf(
    Screen.Dashboard,
    Screen.Attendance,
    Screen.Leave,
    Screen.Task,
    Screen.Visit,
    Screen.Announcement
)

val bottomNavItems = listOf(
    Screen.Dashboard,
    Screen.Attendance,
    Screen.Leave,
    Screen.Task,
    Screen.Visit,
    Screen.Announcement
)
