package com.myapplication.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val PrimaryLinearColor = Brush.linearGradient(
    colors = listOf(
        Color(0xFF8A5CF6),
        Color(0xFF3B82F6)
    ),
)

val UnselectedBackgroundColor = Brush.linearGradient(
    colors = listOf(
        Color.Transparent,
        Color.Transparent
    )
)

val HomeLinearColor = Brush.linearGradient(
    colors = listOf(
        Color(0xFF180728),
        Color(0xFF0E0E10),
        Color(0xFF0E0E10)
    ),
)