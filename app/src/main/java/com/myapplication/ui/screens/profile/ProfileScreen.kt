package com.myapplication.ui.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myapplication.ui.components.Screen
import com.myapplication.ui.theme.MyApplicationTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Screen(modifier = modifier) {
        Text(text = "Nội dung Trang cá nhân")
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    MyApplicationTheme {
        ProfileScreen()
    }
}
