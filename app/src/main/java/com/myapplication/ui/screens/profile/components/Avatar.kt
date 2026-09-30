package com.myapplication.ui.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.myapplication.model.User

@Composable
fun Avatar(
    user: User
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 12.dp)
                .size(104.dp)
                .clip(RoundedCornerShape(52.dp))
            ,
            contentScale = ContentScale.Crop,
        )
        Text(
            text = user.name,
            color = Color.White,
            lineHeight = 20.sp,
            letterSpacing = 0.2.sp,
        )
        Text(
            text = user.desc,
            color = Color.Gray,
            lineHeight = 20.sp,
            letterSpacing = 0.2.sp,
        )
    }
}