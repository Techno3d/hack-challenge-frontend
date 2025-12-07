package com.example.hackchallengenewsfrontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.hackchallengenewsfrontend.R
import com.example.hackchallengenewsfrontend.ui.theme.Articles
import com.example.hackchallengenewsfrontend.ui.theme.Primary
import com.example.hackchallengenewsfrontend.ui.theme.Secondary

@Composable
fun NewsCard(
    title: String,
    thumbnailUrl: String,
    thumbnailDescription: String? = null,
    author: String,
    newsSource: String,
    date: String,
    modifier : Modifier = Modifier,
    onFavoriteClick: () -> Unit = {},
    isFavorited: Boolean = false,
    isCompact: Boolean = false,
    isAudio: Boolean = false,
    isPlaying: Boolean = false,
    onCardClick: () -> Unit = {},
    onPlayButtonClicked: () -> Unit = {}
) {
    fun getLogoForSource(sourceName: String): Int {
        return when {
            sourceName.contains("Cornell Daily Sun") -> R.drawable.cornell_daily_sun_logo
            sourceName.contains("Cornell Chronicle") -> R.drawable.cornell_chronicle_logo
            sourceName.contains("The Ithaca Voice") -> R.drawable.ithaca_voice_logo
            else -> R.drawable.ic_launcher_foreground
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(if(isCompact) 4.dp else 8.dp),
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Articles)
            .clickable { onCardClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnailUrl.ifEmpty { getLogoForSource(newsSource) })
                .crossfade(false)
                .build(),
            //model = thumbnailUrl.ifEmpty { getLogoForSource(newsSource)},
            contentDescription = thumbnailDescription,
            contentScale = ContentScale.Crop,
            error = painterResource(id = getLogoForSource(newsSource)),
            placeholder = painterResource(id = getLogoForSource(newsSource)),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(if(isCompact) 4.dp else 12.dp),
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(newsSource, fontSize = if(isCompact) 10.sp else 14.sp, textAlign = TextAlign.Left, color = Primary, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(title, fontSize = if(isCompact) 14.sp else 18.sp, textAlign = TextAlign.Left, color = Primary, lineHeight = if(isCompact) 16.sp else 20.sp, fontWeight = FontWeight.SemiBold, maxLines = if(isCompact) 4 else 5, overflow = TextOverflow.Ellipsis)
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                if(!isAudio) {
                    Text("$date \u2022 $author", fontSize = 12.sp, color = Secondary, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        IconButton(onClick = { onPlayButtonClicked() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cardplaybutton),
                                contentDescription = "Play Button",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                            if(!isPlaying) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_playbutton),
                                    contentDescription = "Play Button",
                                    tint = Color.Black,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            else{
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_pausebutton),
                                    contentDescription = "Pause Button",
                                    tint = Color.Black,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Text(text = " \u2022   $date", color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Save Button",
                    tint = if(isFavorited) Color.White else Color.LightGray,
                    modifier = Modifier.size(if(isCompact) 18.dp else 24.dp)
                        .clickable(onClick = { onFavoriteClick() })
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview(){
    NewsCard(
        title = "Winter storm snarls flights for post-Thanksgiving travelers in  ChicagoChicagoChicagoChicagoChicagoChicagoChicagoChicagoChicagoChicago",
        thumbnailUrl = "",
        author = "Goated Author Chicago Chicago ChicagoChicago",
        newsSource = "Cornell Chronicle",
        date = "no",
        isCompact = true,
        onCardClick = {},
        onFavoriteClick = {},
        isFavorited = true,
        onPlayButtonClicked = {}
    )
}