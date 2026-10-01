package com.myapplication.ui.screens.blank

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.myapplication.ui.components.Screen

@Composable
fun BlankScreen(modifier: Modifier = Modifier) {
    Screen(
        modifier = modifier.background(Color.White),
        safeTop = false,
        safeBottom = false,
        safeLeft = false,
        safeRight = false
    ) {}
}
