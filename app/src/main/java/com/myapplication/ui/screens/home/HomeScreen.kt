package com.myapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapplication.ui.components.Screen
import com.myapplication.ui.components.StatusBarSpacer
import com.myapplication.ui.screens.home.components.Header
import com.myapplication.ui.screens.home.components.MusicTypeList
import com.myapplication.ui.theme.HomeLinearColor
import com.myapplication.ui.theme.MyApplicationTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        modifier = modifier,
        uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent)-> Unit
) {
    Screen(modifier = modifier.background(brush = HomeLinearColor), safeTop = false) {
        Column(modifier = Modifier.fillMaxSize()) {
            StatusBarSpacer()
            Header(
                searchText = uiState.searchText,
                onSearchTextChanged = { text ->
                    onEvent(HomeUiEvent.HeaderEvent.OnSearchTextChanged(text))
                },
                onSearchClick = {
                    onEvent(HomeUiEvent.HeaderEvent.OnSearchClick)
                }
            )
            MusicTypeList(
                items = uiState.itemList,
                selectedItem = uiState.selectedMusicType,
                onClick = { type -> onEvent(HomeUiEvent.MusicListEvent.OnMusicTypeSelected(type)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MyApplicationTheme {
        HomeContent(
            uiState = HomeUiState(
                itemList = listOf("All", "Chill", "Workout", "Focus", "Other"),
                selectedMusicType = "Chill",
                searchText = ""
            ),
            onEvent = {}
        )
    }
}
