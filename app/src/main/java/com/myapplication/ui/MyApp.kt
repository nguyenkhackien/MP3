package com.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.rememberGraphicsLayer
import com.myapplication.ui.modifiers.GlassBackdrop
import com.myapplication.ui.modifiers.glassSource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import com.myapplication.navigation.AppNavHost
import com.myapplication.navigation.AppNavigator
import com.myapplication.navigation.BottomNavItem
import com.myapplication.navigation.rememberAppNavigationState
import com.myapplication.ui.components.LocalScreenPadding
import com.myapplication.ui.components.FloatingBottomBar
import kotlinx.coroutines.launch

@Composable
fun MyApp(navigator: AppNavigator) {
    val navigationState = rememberAppNavigationState()
    val scope = rememberCoroutineScope()
    val backdropLayer = rememberGraphicsLayer()
    val backdrop = remember(backdropLayer) { GlassBackdrop(backdropLayer) }
    val systemPadding = WindowInsets.safeDrawing.asPaddingValues()
    val layoutDirection = LocalLayoutDirection.current
    val items = BottomNavItem.items
    val selectedRoute = if (navigationState.isInTabFlow) {
        items.firstOrNull { it.destination == navigationState.selectedTab }?.route
    } else null

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            // The navigation viewport is edge-to-edge. Insets are opt-in inside Screen.
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (selectedRoute != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        FloatingBottomBar(
                            backdrop = backdrop,
                            selectedRoute = selectedRoute,
                            items = items,
                            onItemClick = { item ->
                                if (selectedRoute != item.route) {
                                    scope.launch { navigator.selectTab(item.destination) }
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            val screenPadding = PaddingValues.Absolute(
                left = systemPadding.calculateLeftPadding(layoutDirection),
                top = systemPadding.calculateTopPadding(),
                right = systemPadding.calculateRightPadding(layoutDirection),
                bottom = maxOf(systemPadding.calculateBottomPadding(), innerPadding.calculateBottomPadding())
            )
            CompositionLocalProvider(LocalScreenPadding provides screenPadding) {
                // Record the entire content outside NavDisplay's animated graphics layers.
                // Screen continues to control its own safe edges through LocalScreenPadding.
                Box(Modifier.fillMaxSize().glassSource(backdrop)) {
                    AppNavHost(
                        navigator = navigator,
                        navigationState = navigationState,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
