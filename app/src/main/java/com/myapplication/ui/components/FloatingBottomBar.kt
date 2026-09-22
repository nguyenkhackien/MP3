package com.myapplication.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.myapplication.ui.theme.MyApplicationTheme
import com.myapplication.navigation.BottomNavItem
import com.myapplication.ui.theme.PrimaryLinearColor

@Composable
fun FloatingBottomBar(
    selectedRoute: String?,
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) return

    val selectedIndex = items.indexOfFirst { it.route == selectedRoute }.coerceAtLeast(0)

    val animatedIndex by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "LiquidAnimation"
    )

    Box(
        modifier = modifier
            .padding(vertical = 16.dp)
            .height(60.dp)
            .width(224.dp)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.3f),
                        Color.White.copy(alpha = 0.05f)
                    )
                ),
                shape = RoundedCornerShape(32.dp)
            )
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFF1E1E24).copy(alpha = 0.5f))
            .padding(6.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val tabWidth = maxWidth / items.size
            val indicatorOffset = tabWidth * animatedIndex
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(tabWidth)
                    .fillMaxHeight()
                    .padding(2.dp)
                    .background(
                        brush = PrimaryLinearColor,
                        shape = CircleShape
                    )
            )
        }

        Row(
            modifier = Modifier.fillMaxSize().selectableGroup(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEach { item ->
                val isSelected = selectedRoute == item.route

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .selectable(
                            selected = isSelected,
                            role = Role.Tab,
                            onClick = { onItemClick(item) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title,
                        tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FloatingBottomBarPreview() {
    MyApplicationTheme {
        FloatingBottomBar(
            selectedRoute = BottomNavItem.Home.route,
            items = listOf(BottomNavItem.Home, BottomNavItem.Library, BottomNavItem.Profile),
            onItemClick = {}
        )
    }
}
