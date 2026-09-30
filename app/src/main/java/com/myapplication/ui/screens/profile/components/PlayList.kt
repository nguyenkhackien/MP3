package com.myapplication.ui.screens.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.myapplication.R
import com.myapplication.ui.screens.home.components.MusicCard
import com.myapplication.ui.screens.profile.PlayListItem

@Composable
private fun PlayListItem(
    item: PlayListItem,
    onClick: (id:String) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = {onClick(item.id)}),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
            ,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp
            )
            Text(
                text = "${item.songCount} songs",
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.arrow_right_ic),
            contentDescription = "Arrow right",
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun PlayList(
    list: List<PlayListItem>,
    onClick: (id:String) -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp,top=20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Play list",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(list) { item ->
                PlayListItem(item,onClick)
            }
        }
    }
}