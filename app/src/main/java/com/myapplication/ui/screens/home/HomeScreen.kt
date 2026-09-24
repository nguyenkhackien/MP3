package com.myapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapplication.ui.components.Screen
import com.myapplication.ui.components.LocalScreenPadding
import com.myapplication.ui.screens.home.components.Header
import com.myapplication.ui.screens.home.components.MusicForYouList
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
    val bottomPadding = LocalScreenPadding.current?.calculateBottomPadding()
        ?: WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
    Screen(
        modifier = modifier.background(brush = HomeLinearColor),
        safeBottom = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = bottomPadding)
        ) {
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
            MusicForYouList(
                items = uiState.myMusicList,
                onClick = { music ->
                    onEvent(HomeUiEvent.OnSelectMusic(music))
                }
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
