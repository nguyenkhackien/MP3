package com.myapplication.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.myapplication.model.Music
import com.myapplication.model.User
import com.myapplication.ui.screens.library.LibraryEvent
import com.myapplication.ui.screens.library.SortDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PlayListItem(
    val id: String = "",
    val title: String = "",
    val songCount: Int = 0,
    val imageUrl: String = ""
)

data class ProfileUiState(
    val playList: List<PlayListItem> = emptyList(),
    val user: User = User.EMPTY
)

sealed interface ProfileEvent{
    data class OnSelectPlayList(val id: String) : ProfileEvent

    data object OnViewMore : ProfileEvent

}

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel(){
    private val _uiState = MutableStateFlow(ProfileUiState(
        user = User(
            name = "Nova Rae",
            desc = "Music is my Therapy",
            avatarUrl = "https://picsum.photos/seed/noi-nay-co-anh/300/300"
        ),
        playList = listOf(
            PlayListItem(
                id = "1",
                title = "Lo-Fi Chill Zone",
                songCount = 24,
                imageUrl = "https://picsum.photos/seed/lofi-chill/300/300"
            ),
            PlayListItem(
                id = "2",
                title = "After Hours",
                songCount = 15,
                imageUrl = "https://picsum.photos/seed/after-hours/300/300"
            ),
            PlayListItem(
                id = "3",
                title = "Weekend Hype",
                songCount = 19,
                imageUrl = "https://picsum.photos/seed/weekend-hype/300/300"
            )
        )
    ))

    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onEvent(event: ProfileEvent) {
        when(event){
            is ProfileEvent.OnSelectPlayList -> handleSelectMusic(event.id)
            is ProfileEvent.OnViewMore -> handleViewMore()
        }
    }

    fun handleSelectMusic(id: String){

    }

    fun handleViewMore(){

    }
}
