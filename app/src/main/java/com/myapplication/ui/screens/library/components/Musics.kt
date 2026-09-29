package com.myapplication.ui.screens.library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.myapplication.R
import com.myapplication.core.tool.toFormattedDuration
import com.myapplication.model.Music
import com.myapplication.ui.components.BaseButton
import com.myapplication.ui.screens.home.components.TrendingItem

@Composable
private fun MusicItem(item: Music,onClick:(Music)-> Unit){
    Row(
        Modifier.height(60.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(60.dp).clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.title,
                color = Color.White
            )
            Text(
                text = "${item.author} - ${item.duration.toFormattedDuration()}",
                color = Color.Gray
            )
        }
        BaseButton(
            onClick = { onClick(item) },
            Modifier.size(32.dp),) {
            Icon(
                painter = painterResource(id = R.drawable.play_ic),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
@Composable
fun Musics(
    items: List<Music>,
    onClick: (Music) -> Unit,
    onSort: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sort_ic),
                contentDescription = "",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp).clickable(enabled = true,onClick = {onSort()}).minimumInteractiveComponentSize(),
            )
            Text(text = "Recents",color= Color.Gray)
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(items) { item ->
                MusicItem(item,onClick)
            }
        }
    }
}