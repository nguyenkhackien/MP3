package com.myapplication.ui.modifiers

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.glassBlur(blurRadius: Float = 30f): Modifier =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && blurRadius > 0f) {
        graphicsLayer {
            renderEffect = RenderEffect
                .createBlurEffect(
                    blurRadius,
                    blurRadius,
                    Shader.TileMode.MIRROR
                )
                .asComposeRenderEffect()
        }
    } else {
        this
    }