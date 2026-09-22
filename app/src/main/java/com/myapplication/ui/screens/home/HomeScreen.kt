package com.myapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myapplication.ui.components.Screen
import com.myapplication.ui.components.StatusBarSpacer
import com.myapplication.ui.screens.home.components.Header
import com.myapplication.ui.screens.home.components.MusicTypeList
import com.myapplication.ui.theme.HomeLinearColor
import com.myapplication.ui.theme.MyApplicationTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val itemList = listOf("All", "Chill", "Workout", "Focus", "Other")

    var selectedMusicType by rememberSaveable { mutableStateOf("All") }
    
    Screen(modifier = modifier.background(brush = HomeLinearColor), safeTop = false) {
        Column(modifier = Modifier.fillMaxSize()) {
            StatusBarSpacer()
            Header()
            MusicTypeList(
                items = itemList,
                selectedItem = selectedMusicType,
                onClick = { selectedMusicType = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MyApplicationTheme {
        HomeScreen()
    }
}
