package com.myapplication.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.contentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.myapplication.R
import com.myapplication.ui.components.BaseButton
import com.myapplication.ui.components.Screen
import com.myapplication.ui.screens.profile.components.Avatar
import com.myapplication.ui.screens.profile.components.PlayList
import com.myapplication.ui.theme.HomeLinearColor
import com.myapplication.ui.theme.MyApplicationTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileContent(
        modifier = modifier,
        uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun ProfileContent(
    modifier: Modifier,
    uiState: ProfileUiState,
    onEvent: (ProfileEvent)-> Unit
){
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
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical =8.dp ),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BaseButton(
                    onClick = { onEvent(ProfileEvent.OnViewMore) },
                    Modifier.size(36.dp).fillMaxWidth(),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.more_ic),
                        contentDescription = "More options",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Avatar(uiState.user)
            PlayList(uiState.playList, {id -> onEvent(ProfileEvent.OnSelectPlayList(id))})
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    MyApplicationTheme {
        ProfileScreen()
    }
}
