package com.example.hackchallengenewsfrontend.ui.components

import android.R.attr.end
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.hackchallengenewsfrontend.R
import com.example.hackchallengenewsfrontend.ui.theme.Articles
import com.example.hackchallengenewsfrontend.ui.theme.HackChallengeNewsFrontendTheme
import com.example.hackchallengenewsfrontend.ui.theme.Primary
import com.example.hackchallengenewsfrontend.ui.theme.Secondary
import com.example.hackchallengenewsfrontend.utils.getLogoForSource

@Composable
fun CompactNewsCard(
    newsSource: String,
    title: String,
    author: String,
    thumbnailUrl: String,
    thumbnailDescription: String,
    date: String,
    onCardClick: () -> Unit,
    onFavoriteClick: () -> Unit = {},
    isFavorited: Boolean = false,
    isAudio: Boolean = false,
    isPlaying: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Articles)
            .clickable { onCardClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = thumbnailUrl.ifEmpty { getLogoForSource(newsSource) },
            contentDescription = thumbnailDescription,
            contentScale = ContentScale.Crop,
            error = painterResource(id = getLogoForSource(newsSource)),
            placeholder = painterResource(id = getLogoForSource(newsSource)),
            modifier = Modifier
                .width(115.dp)
                .height(115.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.width(13.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp)
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = newsSource,
                    fontSize = 12.sp,
                    color = Primary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = title,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    color = Primary,
                    modifier = Modifier
                        .padding(end = 12.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 12.dp)
            ) {
                if (!isAudio) {
                    Text(
                        text = "$date  \u2022  $author",
                        fontSize = 12.sp,
                        color = Secondary,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {/* TODO: Play Functionality */ },
                            modifier = Modifier.size(28.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cardplaybutton),
                                contentDescription = "Play Button",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            if (!isPlaying) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_playbutton),
                                    contentDescription = "Play Button",
                                    tint = Color.Black,
                                    modifier = Modifier.size(12.dp)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_pausebutton),
                                    contentDescription = "Pause Button",
                                    tint = Color.Black,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Text(text = "\u2022  $date", color = Color.White, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Save Button",
                    tint = if (isFavorited) Color.White else Color.LightGray,
                    modifier = Modifier.size(18.dp)
                        .clickable(onClick = { onFavoriteClick() })
                )
            }
        }
    }

}

@Preview()
@Composable
fun CompactNewsCardPreview() {
    CompactNewsCard(
        newsSource = "Cornell Chronicle",
        title = "Winter storm snarls flights for post-Thanksgiving travelers in  ChicagoChicagoChicagoChicagoChicagoChicagoChicagoChicagoChicagoChicago",
        author = "Goated Author Chicago Chicago ChicagoChicago",
        thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
        thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
        date = "no",
        onCardClick = {})
}



