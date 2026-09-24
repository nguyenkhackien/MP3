package com.myapplication.ui.screens.home

import androidx.lifecycle.ViewModel
import com.myapplication.model.Music
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
    val isLoading: Boolean = false,
    val myMusicList: List<Music> = emptyList()
)

sealed interface HomeUiEvent {
    sealed interface HeaderEvent : HomeUiEvent {
        data class OnSearchTextChanged(val text: String) : HeaderEvent
        data object OnSearchClick : HeaderEvent
    }

    sealed interface MusicListEvent : HomeUiEvent {
        data class OnMusicTypeSelected(val type: String) : MusicListEvent
    }

    data class OnSelectMusic(val music: Music) : HomeUiEvent
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            itemList = listOf("All", "Chill", "Workout", "Focus", "Other"),
            myMusicList = listOf(
                Music(
                    title = "Nơi Này Có Anh",
                    desc = "Bản pop ballad lãng mạn về tình yêu đôi lứa.",
                    musician = "Sơn Tùng M-TP",
                    author = "Sơn Tùng M-TP",
                    duration = 296000L,
                    audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    album = "m-tp M-TP",
                    imageUrl = "https://picsum.photos/seed/noi-nay-co-anh/300/300"
                ),
                Music(
                    title = "Nàng Thơ",
                    desc = "Giai điệu nhẹ nhàng, sâu lắng mang phong cách Ballad.",
                    musician = "Hoàng Dũng",
                    author = "Hoàng Dũng",
                    duration = 252000L,
                    audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
                    album = "25",
                    imageUrl = "https://picsum.photos/seed/nang-tho/300/300"
                ),
                Music(
                    title = "Tháng Tư Là Lời Nói Dối Của Em",
                    desc = "Bài hát nhẹ nhàng về những hoài niệm mùa xuân.",
                    musician = "Hà Anh Tuấn",
                    author = "Phạm Toàn Thắng",
                    duration = 270000L,
                    audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
                    album = "Fragile",
                    imageUrl = "https://picsum.photos/seed/thang-tu/300/300"
                ),
                Music(
                    title = "Có Chắc Yêu Là Đây",
                    desc = "Bài hát R&B/Pop sôi động, tươi trẻ.",
                    musician = "Sơn Tùng M-TP",
                    author = "Sơn Tùng M-TP",
                    duration = 202000L,
                    audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
                    album = "Single 2020",
                    imageUrl = "https://picsum.photos/seed/co-chac-yeu-la-day/300/300"
                ),
                Music(
                    title = "Vì Anh Đâu Biết",
                    desc = "Bản Indie Pop nhẹ nhàng ngập tràn cảm xúc.",
                    musician = "Madihu ft. Vũ",
                    author = "Madihu",
                    duration = 218000L,
                    audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
                    album = "Cộng Cà Phê Sessions",
                    imageUrl = "https://picsum.photos/seed/vi-anh-dau-biet/300/300"
                )
            )),
    )

    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.HeaderEvent -> handleHeaderEvent(event)
            is HomeUiEvent.MusicListEvent -> handleMusicListEvent(event)
            is HomeUiEvent.OnSelectMusic -> handleSelectMusic(event)
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

    private fun handleSelectMusic(event: HomeUiEvent.OnSelectMusic){

    }
}
