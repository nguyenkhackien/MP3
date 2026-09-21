package com.myapplication.model

import androidx.annotation.DrawableRes
import com.myapplication.R

sealed class BottomNavItem(val route: String, val title: String, @DrawableRes val iconRes: Int) {
    object Home : BottomNavItem("home", "Home",  R.drawable.home_ic)
    object Library : BottomNavItem("library", "Library", R.drawable.library_ic)
    object Profile : BottomNavItem("profile", "Profile", R.drawable.profile_ic)
}