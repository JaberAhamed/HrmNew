package com.mall.hrmnew.ui.components.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mall.hrmnew.navigation.BottomNavItem
import com.mall.hrmnew.navigation.Screen
import com.mall.hrmnew.navigation.bottomNavItems

/**
 * Material 3 default bottom navigation bar component
 */
@Composable
fun CenteredBottomNavigationBar(
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,

    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint =  if(currentScreen == item.screen) MaterialTheme.colorScheme.background else
                            LocalContentColor.current
                    )
                },
                label = {
                    Text(item.label, fontSize = 11.sp)
                },
                selected = currentScreen == item.screen,
                onClick = { onTabSelected(item.screen) }
            )
        }
    }
}

/**
 * Scaffold with bottom navigation bar
 */
@Composable
fun CenteredBottomNavScaffold(
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit,
    content: @Composable (paddingValues: androidx.compose.foundation.layout.PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            CenteredBottomNavigationBar(
                currentScreen = currentScreen,
                onTabSelected = onTabSelected
            )
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}
