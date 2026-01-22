package com.mall.hrmnew

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.navigation.rememberNavigationManager
import com.mall.hrmnew.ui.screens.announcement.AnnouncementScreen
import com.mall.hrmnew.ui.screens.attendance.AttendanceScreen
import com.mall.hrmnew.ui.screens.auth.LandingScreen
import com.mall.hrmnew.ui.screens.auth.LoginScreen
import com.mall.hrmnew.ui.screens.auth.SplashScreen
import com.mall.hrmnew.ui.screens.dashboard.DashboardScreen
import com.mall.hrmnew.ui.screens.leave.LeaveScreen
import com.mall.hrmnew.ui.screens.task.TaskScreen
import com.mall.hrmnew.ui.screens.visit.VisitScreen
import com.mall.hrmnew.ui.theme.AppTheme
import com.mall.hrmnew.viewmodel.announcement.AnnouncementViewModel
import com.mall.hrmnew.viewmodel.attendance.AttendanceViewModel
import com.mall.hrmnew.viewmodel.auth.LoginViewModel
import com.mall.hrmnew.viewmodel.dashboard.DashboardViewModel
import com.mall.hrmnew.viewmodel.leave.LeaveViewModel
import com.mall.hrmnew.viewmodel.task.TaskViewModel
import com.mall.hrmnew.viewmodel.visit.VisitViewModel

@Composable
fun App() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navManager = rememberNavigationManager(startDestination = Screen.Splash)
            val currentScreen = navManager.currentDestination

            // Track login state
            var userLoggedIn by remember { mutableStateOf(false) }

            // Authentication Flow
            if (!userLoggedIn) {
                when (currentScreen) {
                    is Screen.Splash -> {
                        SplashScreen(
                            onNavigateToLanding = {
                                navManager.navigate(Screen.Landing)
                            }
                        )
                    }
                    is Screen.Landing -> {
                        LandingScreen(
                            onNavigateToLogin = {
                                navManager.navigate(Screen.Login)
                            }
                        )
                    }
                    is Screen.Login -> {
                        val viewModel = remember { LoginViewModel() }
                        LoginScreen(
                            onLoginSuccess = {
                                userLoggedIn = true
                                navManager.navigateAndClearBackStack(Screen.Dashboard)
                            }
                        )
                    }
                    else -> {
                        // Should not happen in auth flow
                        SplashScreen(
                            onNavigateToLanding = {
                                navManager.navigate(Screen.Landing)
                            }
                        )
                    }
                }
            } else {
                // Main App Flow with Bottom Navigation
                MainApp(navManager = navManager)
            }
        }
    }
}

@Composable
fun MainApp(navManager: com.mall.hrmnew.navigation.NavigationManager) {
    val currentScreen = navManager.currentDestination

    // Show content based on current screen
    when (currentScreen) {
        is Screen.Dashboard -> {
            val viewModel = remember { DashboardViewModel() }
            DashboardScreen(
                viewModel = viewModel,
                onTabSelected = { screen ->
                    navManager.navigate(screen)
                }
            )
        }
        is Screen.Attendance -> {
            val viewModel = remember { AttendanceViewModel() }
            AttendanceScreen(
                viewModel = viewModel,
                onTabSelected = { screen ->
                    navManager.navigate(screen)
                }
            )
        }
        is Screen.Leave -> {
            val viewModel = remember { LeaveViewModel() }
            LeaveScreen(
                viewModel = viewModel,
                onTabSelected = { screen ->
                    navManager.navigate(screen)
                }
            )
        }
        is Screen.Task -> {
            val viewModel = remember { TaskViewModel() }
            TaskScreen(
                viewModel = viewModel,
                onTabSelected = { screen ->
                    navManager.navigate(screen)
                }
            )
        }
        is Screen.Visit -> {
            val viewModel = remember { VisitViewModel() }
            VisitScreen(
                viewModel = viewModel,
                onTabSelected = { screen ->
                    navManager.navigate(screen)
                }
            )
        }
        is Screen.Announcement -> {
            val viewModel = remember { AnnouncementViewModel() }
            AnnouncementScreen(
                viewModel = viewModel,
                onTabSelected = { screen ->
                    navManager.navigate(screen)
                }
            )
        }
        else -> {
            // Fallback to Dashboard
            val viewModel = remember { DashboardViewModel() }
            DashboardScreen(
                viewModel = viewModel,
                onTabSelected = { screen ->
                    navManager.navigate(screen)
                }
            )
        }
    }
}
