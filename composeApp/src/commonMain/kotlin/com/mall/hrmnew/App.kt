package com.mall.hrmnew

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.navigation.rememberNavigationManager
import com.mall.hrmnew.ui.appinterface.AppExit
import com.mall.hrmnew.ui.components.navigation.NavDrawerScaffold
import com.mall.hrmnew.ui.screens.announcement.AnnouncementScreen
import com.mall.hrmnew.ui.screens.attendance.AttendanceScreen
import com.mall.hrmnew.ui.screens.auth.LandingScreen
import com.mall.hrmnew.ui.screens.auth.LocationPermissionScreen
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
fun App(appExit: AppExit) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navManager = rememberNavigationManager(startDestination = Screen.Splash)
            val currentScreen = navManager.currentDestination

            // Track login and location permission state
            var userLoggedIn by remember { mutableStateOf(false) }
            var locationPermissionGranted by remember { mutableStateOf(false) }

            // Authentication Flow
            if (!userLoggedIn) {
                when (currentScreen) {
                    is Screen.Splash -> {
                        SplashScreen(
                            onNavigateToLanding = {
                                navManager.navigate(Screen.LocationPermission)
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
                                navManager.navigate(Screen.Dashboard)
                            }
                        )
                    }
                    is Screen.LocationPermission -> {
                        LocationPermissionScreen(
                            onAllowLocation = {
                                locationPermissionGranted = true
                                navManager.navigateAndClearBackStack(Screen.Login)
                            },
                            onExitApp = {
                                // Exit app - reset to login screen
                                userLoggedIn = false
                                appExit.exit()

                            }
                        )
                    }
                    else -> {
                        // Should not happen in auth flow
                        SplashScreen(
                            onNavigateToLanding = {
                                navManager.navigate(Screen.LocationPermission)
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
    val drawerState = rememberDrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    NavDrawerScaffold(
        currentScreen = currentScreen,
        onScreenSelected = { screen ->
            navManager.navigate(screen)
        },
        drawerState = drawerState
    ) {
        // Show content based on current screen
        when (currentScreen) {
            is Screen.Dashboard -> {
                val viewModel = remember { DashboardViewModel() }
                DashboardScreen(
                    viewModel = viewModel,
                    onMenuClick = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )
            }
            is Screen.Attendance -> {
                val viewModel = remember { AttendanceViewModel() }
                AttendanceScreen(
                    viewModel = viewModel,
                    onBackClick = { navManager.navigateUp() }
                )
            }
            is Screen.Leave -> {
                val viewModel = remember { LeaveViewModel() }
                LeaveScreen(
                    viewModel = viewModel,
                    onBackClick = { navManager.navigateUp() }
                )
            }
            is Screen.Task -> {
                val viewModel = remember { TaskViewModel() }
                TaskScreen(
                    viewModel = viewModel,
                    onBackClick = { navManager.navigateUp() }
                )
            }
            is Screen.Visit -> {
                val viewModel = remember { VisitViewModel() }
                VisitScreen(
                    viewModel = viewModel,
                    onBackClick = { navManager.navigateUp() }
                )
            }
            is Screen.Announcement -> {
                val viewModel = remember { AnnouncementViewModel() }
                AnnouncementScreen(
                    viewModel = viewModel,
                    onBackClick = { navManager.navigateUp() }
                )
            }
            else -> {
                // Fallback to Dashboard
                val viewModel = remember { DashboardViewModel() }
                DashboardScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}
