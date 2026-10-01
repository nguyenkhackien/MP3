package com.myapplication.navigation

import androidx.annotation.DrawableRes
import androidx.navigation3.runtime.NavKey
import com.myapplication.R
import kotlinx.serialization.Serializable

/** Routing data only. Register the corresponding UI in AppNavHost. */
@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object Home : AppRoute

    @Serializable
    data object Library : AppRoute

    @Serializable
    data object Profile : AppRoute

    @Serializable
    data object Blank : AppRoute
}

sealed class BottomNavItem(val route: String, val title: String, val destination: AppRoute, @DrawableRes val iconRes: Int) {
    object Home : BottomNavItem("home", "Home", AppRoute.Home, R.drawable.home_ic)
    object Library : BottomNavItem("library", "Library", AppRoute.Library, R.drawable.library_ic)
    object Profile : BottomNavItem("profile", "Profile", AppRoute.Profile, R.drawable.profile_ic)

    companion object {
        val items = listOf(Home, Library, Profile)
    }
}
