package com.myapplication.ui.screens.library.components

import androidx.compose.foundation.layout.Arrangement
import com.myapplication.ui.components.BaseButton
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapplication.R
import com.myapplication.ui.theme.MyApplicationTheme

@Composable
fun Header(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Your Library",
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        BaseButton(
            onClick = onSearchClick,
            modifier = Modifier.padding(0.dp,0.dp,12.dp,0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search_ic),
                contentDescription = "Tìm kiếm",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        BaseButton(
            onClick = onSearchClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_ic),
                contentDescription = "Tìm kiếm",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    MyApplicationTheme {
        Header("",{},{})
    }
}