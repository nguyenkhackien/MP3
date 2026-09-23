package com.myapplication.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class HomeUiState(
    val itemList: List<String> = emptyList(),
    val selectedMusicType: String = "All",
    val searchText: String = "",
    val isLoading: Boolean = false
)

sealed interface HomeUiEvent {
    sealed interface HeaderEvent : HomeUiEvent {
        data class OnSearchTextChanged(val text: String) : HeaderEvent
        data object OnSearchClick : HeaderEvent
    }

    sealed interface MusicListEvent : HomeUiEvent {
        data class OnMusicTypeSelected(val type: String) : MusicListEvent
    }
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeUiState(itemList = listOf("All", "Chill", "Workout", "Focus", "Other"))
    )

    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.HeaderEvent -> handleHeaderEvent(event)
            is HomeUiEvent.MusicListEvent -> handleMusicListEvent(event)
        }
    }

    private fun handleHeaderEvent(event: HomeUiEvent.HeaderEvent) {
        when (event) {
            is HomeUiEvent.HeaderEvent.OnSearchTextChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(searchText = event.text) }
            }
            HomeUiEvent.HeaderEvent.OnSearchClick -> {}
        }
    }

    private fun handleMusicListEvent(event: HomeUiEvent.MusicListEvent){
        when (event){
            is HomeUiEvent.MusicListEvent.OnMusicTypeSelected -> {
                _uiState.update { currentState ->
                    currentState.copy(selectedMusicType = event.type) }
            }
        }
    }
}
