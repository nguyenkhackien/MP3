package com.myapplication.ui.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapplication.ui.components.LocalScreenPadding
import com.myapplication.ui.components.Screen
import com.myapplication.ui.screens.library.components.Header
import com.myapplication.ui.screens.library.components.MusicTypeList
import com.myapplication.ui.screens.library.components.Musics
import com.myapplication.ui.theme.HomeLinearColor
import com.myapplication.ui.theme.MyApplicationTheme

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LibraryContent(
        modifier = modifier,
        uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun LibraryContent(
    modifier: Modifier = Modifier,
    uiState: LibraryUiState,
    onEvent: (LibraryEvent)-> Unit
) {
    val bottomPadding = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()

    Screen(
        modifier = modifier.background(brush = HomeLinearColor),
        safeBottom = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(bottom = bottomPadding)
        ) {
            Header(
                searchText = uiState.searchText,
                onSearchTextChanged = {text ->
                    onEvent(LibraryEvent.OnSearchTextChanged(text))
                },
                onSearchClick = {
                    onEvent(LibraryEvent.OnSearchClick)
                }
            )
            MusicTypeList(
                uiState.itemList,
                uiState.selectedMusicType,
                {type ->onEvent(LibraryEvent.OnMusicTypeSelected(type))}
            )
            Musics(
                uiState.musicList,
                {music -> onEvent(LibraryEvent.OnSelectMusic(music))},
                onSort = {onEvent(LibraryEvent.OnChangeSortDirection)}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LibraryScreenPreview() {
    MyApplicationTheme {
        LibraryScreen()
    }
}
