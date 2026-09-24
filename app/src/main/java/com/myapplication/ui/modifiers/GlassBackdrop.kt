package com.myapplication.ui.modifiers

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot

/** Shares only the screen content; the bottom bar must not be part of this layer. */
class GlassBackdrop(val layer: GraphicsLayer) {
    var origin by mutableStateOf(Offset.Zero)
    var revision by mutableIntStateOf(0)
}

fun Modifier.glassSource(backdrop: GlassBackdrop): Modifier {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return this
    return onGloballyPositioned { backdrop.origin = it.positionInRoot() }
        .drawWithContent {
            backdrop.layer.record { this@drawWithContent.drawContent() }
            drawLayer(backdrop.layer)
            // Invalidate the glass when images load or the screen scrolls, without
            // subscribing this source to its own invalidation counter.
            Snapshot.withoutReadObservation { backdrop.revision++ }
        }
}

@Composable
fun Modifier.glassBackdrop(backdrop: GlassBackdrop?): Modifier {
    if (backdrop == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        return background(Color(0xFF1E1E24).copy(alpha = 0.85f))
    }
    var origin by remember { mutableStateOf(Offset.Zero) }
    return onGloballyPositioned { origin = it.positionInRoot() }
        .glassBlur(blurRadius = 25f)
        .drawWithContent {
            if (backdrop.revision > 0) {
                val offset = backdrop.origin - origin
                translate(left = offset.x, top = offset.y) {
                    drawLayer(backdrop.layer)
                }
            }
            drawRect(Color(0xFF1E1E24).copy(alpha = 0.2f))
            drawContent()
        }
}
