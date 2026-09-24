package com.myapplication.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.myapplication.R
import com.myapplication.model.Music
import com.myapplication.ui.components.BaseButton
import com.myapplication.ui.theme.MyApplicationTheme
import com.myapplication.ui.theme.PrimaryLinearColor

@Composable
fun MusicItem(
    item: Music,
    onClick: (Music) -> Unit
){
    Card(
        modifier = Modifier.width(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RectangleShape
    ) {
        Box(
            Modifier.size(148.dp,140.dp).clip(RoundedCornerShape(16.dp))
        ){
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            BaseButton(
                onClick = { onClick(item) },
                Modifier.size(32.dp).align(Alignment.BottomEnd).offset(x = (-8).dp, y = (-8).dp),
                background = PrimaryLinearColor
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.play_ic),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Box(Modifier.size(height = 12.dp, width = 0.dp))
        Text(text = item.title, overflow = TextOverflow.Ellipsis, maxLines = 1,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = (-0.2).sp,
                textAlign = TextAlign.Left,
                color = Color.White
            )
        )
        Box(Modifier.size(height = 4.dp, width = 0.dp))
        Text(text = item.desc, overflow = TextOverflow.Ellipsis, maxLines = 1,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Left,
                color = Color(0xFFA1A1AA)
            )
        )
    }
}

@Composable
fun MusicForYouList(
    items: List<Music>,
    onClick: (Music) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        Text(text = "For You",color= Color.White)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items) { item ->
                MusicItem(item,onClick)
            }
        }
    }

}

@Preview
@Composable
private fun MusicForYouListPreview() {
    MyApplicationTheme {
        MusicForYouList(emptyList(),{})
    }
}
