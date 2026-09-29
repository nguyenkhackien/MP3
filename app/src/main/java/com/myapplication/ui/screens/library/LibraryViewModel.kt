package com.myapplication.ui.screens.library

import androidx.lifecycle.ViewModel
import com.myapplication.model.Music
import com.myapplication.ui.screens.home.HomeUiEvent
import com.myapplication.ui.screens.home.HomeUiEvent.HeaderEvent
import com.myapplication.ui.screens.home.HomeUiEvent.MusicListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class SortDirection {
    DECREASE,
    INCREASE
}
data class LibraryUiState(
    val itemList: List<String> = emptyList(),
    val selectedMusicType: String = "All",
    val searchText: String = "",
    val musicList: List<Music> = emptyList(),
    val sortDirection: SortDirection
)

sealed interface LibraryEvent{
    data class OnMusicTypeSelected(val type: String) : LibraryEvent

    data class OnSearchTextChanged(val text: String) : LibraryEvent

    data object OnSearchClick : LibraryEvent

    data class OnSelectMusic(val music: Music) : LibraryEvent

    data object OnChangeSortDirection : LibraryEvent
}

@HiltViewModel
class LibraryViewModel @Inject constructor(): ViewModel(){
    private val _uiState = MutableStateFlow(LibraryUiState(
        itemList = listOf("All", "Play list", "Songs", "Artist"),
        musicList = listOf(
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
        ),
        sortDirection = SortDirection.INCREASE
    ))

    val uiState : StateFlow<LibraryUiState> = _uiState.asStateFlow()

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.OnMusicTypeSelected -> handleSelectedMusicType(event.type)
            is LibraryEvent.OnSearchTextChanged -> handleSearchText(event.text)
            is LibraryEvent.OnSearchClick -> handleSearchClick()
            is LibraryEvent.OnSelectMusic -> handleSelectMusic(event.music)
            is LibraryEvent.OnChangeSortDirection -> handleChangeSortDirection()
        }
    }

    fun handleSelectedMusicType(type: String){
        _uiState.update { currentState ->
            currentState.copy(selectedMusicType = type) }
    }

    fun handleSearchText(text: String){
        _uiState.update { currentState ->
            currentState.copy(selectedMusicType = text) }
    }

    fun handleSearchClick(){}

    fun handleSelectMusic(music: Music){

    }

    fun handleChangeSortDirection(){
        _uiState.update { currentState ->
            val newDirection = if (currentState.sortDirection == SortDirection.INCREASE) {
                SortDirection.DECREASE
            } else {
                SortDirection.INCREASE
            }

            val sortedList = if (newDirection == SortDirection.INCREASE) {
                currentState.musicList.sortedBy { it.title }
            } else {
                currentState.musicList.sortedByDescending { it.title }
            }

            currentState.copy(
                sortDirection = newDirection,
                musicList = sortedList
            )
        }
    }
}