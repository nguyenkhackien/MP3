package com.myapplication.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp

// MyApp cung cấp khoảng an toàn cho cả cây UI, không cần truyền qua từng màn.
// null giúp Screen dùng system insets khi chạy độc lập hoặc trong Preview.
internal val LocalScreenPadding = compositionLocalOf<PaddingValues?> { null }

/**
 * Khung gốc của mỗi màn. Mặc định tránh system bars và các thanh của Scaffold.
 * Left/Right là cạnh vật lý, không đổi ý nghĩa khi ngôn ngữ chuyển sang RTL.
 * Tắt một cạnh cho phép nội dung vẽ phía dưới vùng được bảo vệ ở cạnh đó;
 * không ẩn system bar hay bottom bar.
 */
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    safeTop: Boolean = true,
    safeBottom: Boolean = true,
    safeLeft: Boolean = true,
    safeRight: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val padding = LocalScreenPadding.current ?: WindowInsets.safeDrawing.asPaddingValues()
    val layoutDirection = LocalLayoutDirection.current
    val left = if (safeLeft) padding.calculateLeftPadding(layoutDirection) else 0.dp
    val top = if (safeTop) padding.calculateTopPadding() else 0.dp
    val right = if (safeRight) padding.calculateRightPadding(layoutDirection) else 0.dp
    val bottom = if (safeBottom) padding.calculateBottomPadding() else 0.dp
    val appliedPadding = PaddingValues.Absolute(left, top, right, bottom)

    Box(
        modifier = modifier
            .fillMaxSize()
            .absolutePadding(left = left, top = top, right = right, bottom = bottom)
            .consumeWindowInsets(appliedPadding),
        content = content
    )
}
