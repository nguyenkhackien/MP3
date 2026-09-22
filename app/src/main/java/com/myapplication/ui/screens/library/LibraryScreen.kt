package com.myapplication.ui.screens.library

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myapplication.ui.components.Screen
import com.myapplication.ui.theme.MyApplicationTheme

@Composable
fun LibraryScreen(modifier: Modifier = Modifier) {
    Screen(modifier = modifier) {
        Text(text = "Nội dung Thư viện")
    }
}

@Preview(showBackground = true)
@Composable
private fun LibraryScreenPreview() {
    MyApplicationTheme {
        LibraryScreen()
    }
}
