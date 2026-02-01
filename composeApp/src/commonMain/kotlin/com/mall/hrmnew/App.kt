package com.mall.hrmnew

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mall.hrmnew.data.api.ApiService
import com.mall.hrmnew.data.network.ApiClient
import com.mall.hrmnew.data.repository.AuthRepository
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.navigation.rememberNavigationManager
import com.mall.hrmnew.ui.appinterface.AppExit
import com.mall.hrmnew.ui.components.navigation.CenteredBottomNavScaffold
import com.mall.hrmnew.ui.screens.announcement.AnnouncementScreen
import com.mall.hrmnew.ui.screens.attendance.AttendanceScreen
import com.mall.hrmnew.ui.screens.auth.LandingScreen
import com.mall.hrmnew.ui.screens.auth.LocationPermissionScreen
import com.mall.hrmnew.ui.screens.auth.LoginScreen
import com.mall.hrmnew.ui.screens.auth.SplashScreen
import com.mall.hrmnew.ui.screens.dashboard.DashboardScreen
import com.mall.hrmnew.ui.screens.leave.LeaveScreen
import com.mall.hrmnew.ui.screens.other.OtherScreen
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
import com.mall.hrmnew.permissions.LocationPermissionController
import com.mall.hrmnew.permissions.bothPermissionsGranted
import com.mall.hrmnew.util.UserSharedPreference

@Composable
fun App(
    appExit: AppExit,
    permissionController: LocationPermissionController,
    userSharedPreference: UserSharedPreference
) {
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

            // Initialize dependencies for LoginViewModel
            var loginViewModel by remember { mutableStateOf<LoginViewModel?>(null) }

            // Initialize repository and viewmodel
            LaunchedEffect(Unit) {
                val httpClient = ApiClient.getInstance(userSharedPreference)
                val authApiService = ApiService(httpClient)
                val authRepository = AuthRepository(authApiService)
                loginViewModel = LoginViewModel(authRepository, userSharedPreference)
            }

            // Authentication Flow
            when (currentScreen) {
                is Screen.Splash -> {
                    SplashScreen(
                        onNavigateToLanding = {
                            // Check if user is already logged in
                            val token = userSharedPreference.getToken()
                            val isLoggedIn = token != null

                            // Check location permission status
                            permissionController.refreshPermissionStatus()
                            val locationEnabled = permissionController.bothPermissionsGranted

                            when {
                                isLoggedIn && locationEnabled -> {
                                    // User logged in and location enabled -> go to Home
                                    userLoggedIn = true
                                    locationPermissionGranted = true
                                    navManager.navigateAndClearBackStack(Screen.Dashboard)
                                }
                                isLoggedIn && !locationEnabled -> {
                                    // User logged in but location not enabled -> go to Location Permission
                                    userLoggedIn = true
                                    locationPermissionGranted = false
                                    navManager.navigate(Screen.LocationPermission)
                                }
                                !isLoggedIn && locationEnabled -> {
                                    // User not logged in but location enabled -> go to Landing
                                    userLoggedIn = false
                                    locationPermissionGranted = true
                                    navManager.navigate(Screen.Landing)
                                }
                                else -> {
                                    // User not logged in and location not enabled -> go to Location Permission first
                                    userLoggedIn = false
                                    locationPermissionGranted = false
                                    navManager.navigate(Screen.LocationPermission)
                                }
                            }
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
                    if (loginViewModel != null) {
                        LoginScreen(
                            viewModel = loginViewModel!!,
                            onLoginSuccess = {
                                userLoggedIn = true
                                // Check if location is enabled after login
                                val locationEnabled = permissionController.bothPermissionsGranted
                                if (locationEnabled) {
                                    navManager.navigate(Screen.Dashboard)
                                } else {
                                    navManager.navigate(Screen.LocationPermission)
                                }
                            }
                        )
                    } else {
                        // Show loading while initializing
                        SplashScreen(
                            onNavigateToLanding = {
                                navManager.navigate(Screen.LocationPermission)
                            }
                        )
                    }
                }
                is Screen.LocationPermission -> {
                    LocationPermissionScreen(
                        permissionController = permissionController,
                        onAllowLocation = {
                            locationPermissionGranted = true
                            // After location permission is granted, check if user is logged in
                            val token = userSharedPreference.getToken()
                            if (token != null) {
                                // User is logged in -> navigate to Home
                                userLoggedIn = true
                                navManager.navigateAndClearBackStack(Screen.Dashboard)
                            } else {
                                // User is not logged in -> navigate to Login
                                userLoggedIn = false
                                navManager.navigateAndClearBackStack(Screen.Login)
                            }
                        },
                        onExitApp = {
                            // Exit app - reset to login screen
                            userLoggedIn = false
                            appExit.exit()
                        }
                    )
                }
                is Screen.Dashboard, is Screen.Attendance, is Screen.Leave,
                is Screen.Task, is Screen.Visit, is Screen.Announcement, is Screen.Other -> {
                    // Main App Flow with Bottom Navigation
                    MainApp(navManager = navManager)
                }
                else -> {
                    // Should not happen
                    SplashScreen(
                        onNavigateToLanding = {
                            navManager.navigate(Screen.LocationPermission)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainApp(navManager: com.mall.hrmnew.navigation.NavigationManager) {
    val currentScreen = navManager.currentDestination

    CenteredBottomNavScaffold(
        currentScreen = currentScreen,
        onTabSelected = { screen ->
            navManager.navigate(screen)
        }
    ) { paddingValues ->
        // Show content based on current screen
        when (currentScreen) {
            is Screen.Dashboard -> {
                val viewModel = remember { DashboardViewModel() }
                DashboardScreen(
                    viewModel = viewModel
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
            is Screen.Other -> {
                OtherScreen(
                    onNavigateToTask = {
                        navManager.navigate(Screen.Task)
                    },
                    onNavigateToAnnouncement = {
                        navManager.navigate(Screen.Announcement)
                    },
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
