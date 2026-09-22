package com.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapplication.ui.screens.home.HomeScreen
import com.myapplication.ui.screens.library.LibraryScreen
import com.myapplication.ui.screens.profile.ProfileScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Library.route) { LibraryScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
    }
}
