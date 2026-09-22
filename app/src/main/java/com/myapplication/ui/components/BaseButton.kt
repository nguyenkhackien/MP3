package com.myapplication.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapplication.R
import com.myapplication.ui.theme.MyApplicationTheme
import com.myapplication.ui.theme.PrimaryLinearColor

object BaseButtonDefaults {
    val Shape = RoundedCornerShape(percent = 50)
    val Background = Brush.linearGradient(
        listOf(Color(0xFF16151C), Color(0xFF27252E))
    )
    val Border = BorderStroke(
        1.dp,
        Brush.linearGradient(
            listOf(Color.White.copy(alpha = 0.4f), Color.White.copy(alpha = 0.08f))
        )
    )
    val ContentPadding = PaddingValues(10.dp)
}

@Composable
fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = BaseButtonDefaults.Shape,
    background: Brush = BaseButtonDefaults.Background,
    border: BorderStroke? = BaseButtonDefaults.Border,
    contentPadding: PaddingValues = BaseButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.38f)
            .clip(shape)
            .background(background)
            .then(if (border != null) Modifier.border(border, shape) else Modifier)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0E0E10)
@Composable
private fun BaseButtonPreview() {
    MyApplicationTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BaseButton(
                    onClick = {},
                    background = PrimaryLinearColor,
                    border = null,
                ) { Text("All") }

                BaseButton(onClick = {}) { Text("Chill") }
            }
            BaseButton(
                onClick = {},
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.search_ic),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            BaseButton(onClick = {}, enabled = false) { Text("Disabled") }
        }
    }
}
