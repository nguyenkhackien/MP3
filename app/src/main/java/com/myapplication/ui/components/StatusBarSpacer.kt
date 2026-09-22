package com.myapplication.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StatusBarSpacer(modifier: Modifier = Modifier) {
    val padding = LocalScreenPadding.current ?: WindowInsets.safeDrawing.asPaddingValues()

    val topPadding = padding.calculateTopPadding()

    Spacer(modifier = modifier.height(topPadding))
}