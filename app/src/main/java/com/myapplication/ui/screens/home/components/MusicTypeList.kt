package com.myapplication.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.graphics.Color
import com.myapplication.ui.components.BaseButtonDefaults
import com.myapplication.ui.theme.PrimaryLinearColor
import androidx.compose.ui.unit.dp
import com.myapplication.ui.components.BaseButton

@Composable
fun MusicTypeList(
    items: List<String>,
    selectedItem: String,
    onClick: (String) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues( vertical = 8.dp)
    ) {
        items(items) { item ->
            val isSelected = item == selectedItem
            BaseButton(
                onClick = { onClick(item) },
                modifier = Modifier.semantics { selected = isSelected },
                background = if (isSelected) PrimaryLinearColor else BaseButtonDefaults.Background,
                border = if (isSelected) null else BaseButtonDefaults.Border,
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(text = item,color= Color.White)
            }
        }
    }
}
