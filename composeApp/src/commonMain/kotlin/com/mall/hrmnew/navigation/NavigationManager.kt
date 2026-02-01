package com.mall.hrmnew.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * Simple navigation manager for KMP that maintains a back stack
 */
class NavigationManager(startDestination: Screen) {
    private val _backStack = mutableStateListOf<Screen>(startDestination)
    val backStack: List<Screen> get() = _backStack

    val currentDestination: Screen
        get() = _backStack.last()

    fun navigate(screen: Screen) {
        _backStack.add(screen)
    }

    fun navigateAndClearBackStack(screen: Screen) {
        _backStack.clear()
        _backStack.add(screen)
    }

    fun navigateUp(): Boolean {
        return if (_backStack.size > 1) {
            _backStack.removeLast()
            true
        } else {
            false
        }
    }

    fun popUpTo(screen: Screen, inclusive: Boolean = false) {
        val index = _backStack.indexOf(screen)
        if (index >= 0) {
            val removeFromIndex = if (inclusive) index else index + 1
            repeat(_backStack.size - removeFromIndex) {
                _backStack.removeLast()
            }
        }
    }

    companion object {
        /**
         * Create a NavigationManager with a custom back stack
         */
        fun create(screens: List<Screen>): NavigationManager {
            return NavigationManager(screens.first()).apply {
                _backStack.clear()
                _backStack.addAll(screens)
            }
        }
    }
}

/**
 * Saver for NavigationManager to persist state across configuration changes
 */
private val NavigationManagerSaver: Saver<NavigationManager, *> = listSaver(
    save = { manager -> manager.backStack.map { it.route } },
    restore = { routes ->
        val screens = routes.map { route ->
            when (route) {
                Screen.Splash.route -> Screen.Splash
                Screen.Landing.route -> Screen.Landing
                Screen.Login.route -> Screen.Login
                Screen.LocationPermission.route -> Screen.LocationPermission
                Screen.Dashboard.route -> Screen.Dashboard
                Screen.Attendance.route -> Screen.Attendance
                Screen.Leave.route -> Screen.Leave
                Screen.Task.route -> Screen.Task
                Screen.Visit.route -> Screen.Visit
                Screen.Announcement.route -> Screen.Announcement
                Screen.Other.route -> Screen.Other
                else -> Screen.Splash
            }
        }
        NavigationManager.create(screens)
    }
)

/**
 * Remember a NavigationManager with saveable state
 */
@Composable
fun rememberNavigationManager(
    startDestination: Screen = Screen.Splash
): NavigationManager = rememberSaveable(saver = NavigationManagerSaver) {
    NavigationManager(startDestination)
}
