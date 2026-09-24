package com.myapplication.core.tool

import java.util.Locale

fun toFormattedDuration(value: Long): String {
    val totalSeconds = value / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}