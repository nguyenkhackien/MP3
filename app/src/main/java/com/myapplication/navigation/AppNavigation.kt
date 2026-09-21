package com.myapplication.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myapplication.model.BottomNavItem
import com.myapplication.ui.components.FloatingBottomBar

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Library,
        BottomNavItem.Profile
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Home.route,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(bottom = 72.dp)
            ) {
                composable(BottomNavItem.Home.route) { Text("Nội dung Trang chủ 123") }
                composable(BottomNavItem.Library.route) { Text("Nội dung Thư") }
                composable(BottomNavItem.Profile.route) { Text("Nội dung Trang cá nhân") }
            }
        }

        FloatingBottomBar(
            navController = navController,
            items = items,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

